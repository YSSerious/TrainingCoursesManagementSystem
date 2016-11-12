/* global d3 */

var chart =
        {
            "category3": [{
                    "criterionName": "criterion4",
                    "averageValue": 4.0
                },
                {
                    "criterionName": "criterion5",
                    "averageValue": 12.0
                },
                {
                    "criterionName": "criterion10",
                    "averageValue": 1.0
                }]
        };

var chartd = chart.category3;

var margin = {top: 20, right: 20, bottom: 30, left: 40},
width = 400 - margin.left - margin.right,
        height = 300 - margin.top - margin.bottom;

var svg = d3.select("#chart").append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .style("border", "1px solid black")
        .append("g")
        .attr("transform",
                "translate(" + margin.left + "," + margin.top + ")");

var x = d3.scaleBand()
        .range([0, width])
        .padding(0.1);
var y = d3.scaleLinear()
        .range([height, 0]);

var div = d3.select("#chart").append("div")
        .attr("class", "tooltip")
        .style("opacity", 0);

chartd.forEach(function (d) {
    d.averageValue = +d.averageValue;
});

x.domain(chartd.map(function (d) {
    return d.criterionName;
}));
y.domain([0, d3.max(chartd, function (d) {
        return d.averageValue;
    })]);

var rects = svg.selectAll("rect")
        .data(chartd)
        .enter()
        .append("rect");

var rectAttr = rects
        .data(chartd)
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
            var txt = svg.append("text")
            .text(d.averageValue)
            .attr("class", "chart-tip");
            txt.attr("x", x(d.criterionName) + x.bandwidth()/2 - txt.node().getBBox().width/2);
            txt.attr("y", y(d.averageValue) + (height - y(d.averageValue))/2 + txt.node().getBBox().height/4);
        })
        .on("mouseout", function (d) {
            d3.select("svg").select(".chart-tip").remove();
        });

svg.append("g")
        .attr("transform", "translate(0," + height + ")")
        .call(d3.axisBottom(x));

svg.append("g")
        .call(d3.axisLeft(y));