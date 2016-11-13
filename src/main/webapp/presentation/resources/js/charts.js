/* global d3 */

function drawCriteriaChart(chartdata, wrappingDivId, maxYValue, categoryTitle) {

    var margin = {top: 50, right: 20, bottom: 80, left: 40},
    width = chartdata.length * 250 - margin.left - margin.right,
            height = 400 - margin.top - margin.bottom;

    var x = d3.scaleBand()
            .range([0, width])
            .padding(0.7);
    var y = d3.scaleLinear()
            .range([height, 0]);

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

    var criteria_values = [];
    $.each(chartdata, function (key, value) {
        criteria_values.push(value.criterionName);
    });

    x.domain(criteria_values);
    y.domain([0, maxYValue]);

    canvas.append("g")
            .attr("transform", "translate(0," + height + ")")
            .attr("class", "axis-x")
            .call(d3.axisBottom(x))
            .selectAll("text")
            .attr("transform", "rotate(45)")
            .attr("dx", ".8em")
            .attr("dy", ".15em")
            .style("text-anchor", "start");

    canvas.append("g")
            .attr("class", "axis-y")
            .call(d3.axisLeft(y).ticks(maxYValue).tickSize(-width));

    var title = svg
            .append("text")
            .text(categoryTitle);
    title
            .attr("class", "category-title")
            .attr("x", margin.right + 20)
            .attr("y", 25)
            .style("font-size", "1.2em");

    var rects = canvas.selectAll("rect")
            .data(chartdata)
            .enter()
            .append("rect");

    rects
            .data(chartdata)
            .attr("width", x.bandwidth())
            .attr("height", function (d) {
                return height - y(d.averageValue);
            })
            .attr("x", function (d) {
                return x(d.criterionName);
            })
            .attr("y", function (d) {
                return y(d.averageValue);
            })
            .attr("class", "chart-bar")
            .on("mouseover", function (d) {
                var txt = canvas.append("text")
                        .text(d.averageValue)
                        .attr("class", "chart-tip");
                txt.attr("x", x(d.criterionName) + x.bandwidth() / 2 - txt.node().getBBox().width / 2);
                txt.attr("y", y(d.averageValue) + (height - y(d.averageValue)) / 2 + txt.node().getBBox().height / 4);
            })
            .on("mouseout", function (d) {
                d3.selectAll("svg").select(".chart-tip").remove();
            });
}