/* global d3 */

function drawCriteriaChart(chartdata, wrappingDivId, maxYValue, categoryTitle) {

//    console.log(chartdata);
    var margin = {top: 50, right: 100, bottom: 30, left: 50},
    width = 500,
            height = chartdata.length * 150 - margin.top - margin.bottom;

    var grid = d3.range(maxYValue + 1).map(function (i) {
        return {'x1': 0, 'y1': 0, 'x2': 0, 'y2': height};
    });

    var criteria_values = [];
    $.each(chartdata, function (key, value) {
        criteria_values.push(value.criterionName);
    });


    var x = d3.scaleLinear()
            .domain([0, maxYValue])
            .range([0, width]);

    var y = d3.scaleBand()
            .domain(criteria_values)
            .range([0, height])
            .padding(0.3)
            .paddingOuter(0.5);

    var svg = d3.select(wrappingDivId)
            .append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .style("border", "1px solid #d2d7dd");

    var canvas = svg
            .append("g")
            .attr("transform",
                    "translate(" + margin.left + "," + margin.top + ")");

    chartdata.forEach(function (d) {
        d.averageValue = +d.averageValue;
    });

    var innerTranslate = "translate(" + margin.left + ", 0)";

    canvas.append("g")
            .attr("class", "axis-x")
            .attr("transform", innerTranslate)
            .call(d3.axisTop(x)
                    .ticks(maxYValue));

    canvas.append("g")
            .attr("class", "axis-y")
            .attr("transform", innerTranslate)
            .call(d3.axisLeft(y)
                    .tickSizeOuter(0));

    canvas.append('g')
            .attr("transform", innerTranslate)
            .attr('id', 'grid')
            .selectAll('line')
            .data(grid)
            .enter()
            .append('line')
            .attr("x1", function (d, i) {
                return (i + 1) * width / maxYValue;
            })
            .attr("y1", function (d, i) {
                return d.y1;
            })
            .attr("x2", function (d, i) {
                return (i + 1) * width / maxYValue;
            })
            .attr("y2", function (d, i) {
                return d.y2;
            })
            .style("stroke", "#adadad")
            .style("stroke-width", "1px");

    var title = svg
            .append("text")
            .text(categoryTitle);
    title
            .attr("class", "category-title")
            .attr("x", margin.right + 20)
            .attr("y", 25)
            .style("font-size", "1.2em");

    var rects =
            canvas.append("g")
            .attr("transform", innerTranslate)
            .selectAll("rect")
            .data(chartdata)
            .enter()
            .append("rect");

    rects
            .data(chartdata)
            .attr("width", 0)
            .attr("height", y.bandwidth())
            .attr("x", function (d) {
                return 0;
            })
            .attr("y", function (d) {
                return y(d.criterionName);
            })
            .attr("class", "chart-bar");
    
    //  animation
    rects
            .data(chartdata)
            .transition()
            .duration(2000)
            .attr("width", function (d) {
                return x(d.averageValue);
            });
            
    //  hover
    rects
            .on("mouseover", function (d) {
                var txt = canvas.append("text")
                        .text(d.averageValue)
                        .attr("class", "chart-tip");
                txt.attr("x", x(d.averageValue) / 2 + margin.left - txt.node().getBBox().width / 2);
                txt.attr("y", y(d.criterionName) + y.bandwidth() / 2 + txt.node().getBBox().height / 4);
            })
            .on("mouseout", function (d) {
                d3.selectAll("svg").select(".chart-tip").remove();
            });
}