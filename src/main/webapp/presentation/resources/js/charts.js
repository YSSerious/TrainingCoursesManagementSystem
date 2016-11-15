/* global d3 */

function drawCriteriaChart(chartdata, wrappingDivId, maxYValue, categoryTitle) {
    console.log(d3.values(chartdata));

    var margin = {top: 50, right: 160, bottom: 30, left: 80},
    width = 450,
            height = Object.keys(chartdata).length * 80 - margin.top - margin.bottom;

    var innerTranslate = "translate(" + margin.left + ", 0)";

    var grid = d3.range(maxYValue).map(function (i) {
        return {'x1': 0, 'y1': 0, 'x2': 0, 'y2': height};
    });

    var criteria_values = [];
    $.each(chartdata, function(i, d) {
        $.each(d.studyResults, function(i, d) {
               criteria_values.push(d.criterionName);
        });
    });

    console.log(criteria_values);
    var x = d3.scaleLinear()
            .domain([0, maxYValue])
            .range([0, width]);

    var y = d3.scaleBand()
            .domain(criteria_values)
            .range([0, height])
            .padding(0)
            .paddingOuter(0.5);

    var y0 = d3.scaleBand()
            .range([0, height], .1);

    var svg = d3.select(wrappingDivId)
            .append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .attr("margin", "0 auto")
            .style("border", "1px solid #d2d7dd");

    var canvas = svg
            .append("g")
            .attr("transform",
                    "translate(" + margin.left + "," + margin.top + ")");

    $.each(chartdata, function (key, value) {
        value.averageValue = +value.averageValue;
    });

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

    var rects = canvas.append("g").attr("class", "rects");

    rects.attr("transform", innerTranslate);

    var categories = rects.selectAll(".category")
            .data(chartdata)
            .enter()
            .append("g")
            .attr("class", "category")
            .attr("id", function (d) {
                return d.category;
            });

    var rects = categories.selectAll("rect")
            .data(function (d) {
                return d.studyResults;
            })
            .enter()
            .append("rect")
            .attr("class", "chart-bar")
            .attr("id", function (d) {
                return d.criterionName;
            });

    rects
            .attr("width", function () {
                return 0;
    })
            .attr("height", y.bandwidth())
            .attr("x", 0)
            .attr("y", function (d) {
                return y(d.criterionName);
    })
            .attr("class", "chart-bar");
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
            
    rects
                    .transition()
                    .duration(2000)
                    .attr("width", function (d) {
                        return x(d.averageValue);
                    });

}