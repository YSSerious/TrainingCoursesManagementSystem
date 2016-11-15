/* global d3 */

var sort = {
    NONE: 0,
    INCREASE: 1,
    DECREASE: 2
};

function drawCriteriaChart(chartdata, wrappingDivId, maxYValue, categoryTitle) {
    console.log('chartdata: ', chartdata);

    var margin = {top: 50, right: 160, bottom: 30, left: 80},
    width = 750 - margin.right - margin.left,
            height = Object.keys(chartdata).length * 80 - margin.top - margin.bottom;

    var innerTranslate = "translate(" + margin.left + ", 0)";

    var grid = d3.range(maxYValue).map(function (i) {
        return {'x1': 0, 'y1': 0, 'x2': 0, 'y2': height};
    });

    var criteria = [];
    $.each(chartdata, function (i, d) {
        $.each(d.studyResults, function (i, d) {
            criteria.push(d);
        });
    });

    console.log('criteria_values: ', criteria);
    var x = d3.scaleLinear()
            .domain([0, maxYValue])
            .range([0, width]);

    var y = d3.scaleBand()
            .domain(criteria.map(function (d) {
                return d.criterionName;
            }))
            .range([0, height])
            .padding(0.5)
            .paddingOuter(0.5);

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

    var xAxis = d3.axisTop(x)
            .ticks(maxYValue);

    var yAxis = d3.axisLeft(y)
            .tickSizeOuter(0);

    canvas.append("g")
            .attr("class", "axis-x")
            .attr("transform", innerTranslate)
            .call(xAxis);

    canvas.append("g")
            .attr("class", "axis-y")
            .attr("transform", innerTranslate)
            .call(yAxis);

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

    var bars = canvas.append("g").attr("class", "bars");

    bars.attr("transform", innerTranslate);

    var categories = bars.selectAll(".category")
            .data(chartdata)
            .enter()
            .append("g")
            .attr("class", "category")
            .attr("id", function (d) {
                return d.category;
            });
    ;

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

    var selected = d3.select('#sort-menu').select('ul').selectAll('li').on('click', function () {
        d3.select('#sort-menu').
                insert("button", 'ul')
                .attr("id", "sort-reset")
                .attr("class", "btn btn-link")
                .attr("type", "button")
                .append("span")
                .attr("class", "glyphicon glyphicon-remove");
        var sortType = d3.select(this).select('a').attr('id');
        sortBars(sortType);
    });
    
    d3.select("#sort-reset").on("click", function() {
        sortBars('none');
        d3.select(this).remove();
    });


    function sortBars(sortType) {
        var y0 = y.domain(criteria).copy();
        if (sortType !== 'none') {
            y0 = y.domain(criteria.sort(function (a, b) {
                return sortType === 'increase'
                        ? a.averageValue - b.averageValue
                        : b.averageValue - a.averageValue;
            }).map(function (d) {
                return d.criterionName;
            })).copy();
        }
        canvas.selectAll('.chart-bar')
                .sort(function (a, b) {
                    return y0(b.criteronName) - y0(a.criterionName);
                });

        var transition = canvas.transition().duration(750),
                delay = function (d, i) {
                    return i * 50;
                };

        transition.selectAll('.chart-bar')
                .delay(delay)
                .attr("y", function (d) {
                    return y0(d.criterionName);
                });

        transition.select(".axis-x")
                .call(xAxis)
                .selectAll("g")
                .delay(delay);
    }
}

function drawCategoriesChart(chartdata, wrappingDivId, maxYValue, categoryTitle) {
//    console.log(d3.values(chartdata));

    var margin = {top: 50, right: 160, bottom: 30, left: 80},
    width = 750 - margin.right - margin.left,
            height = Object.keys(chartdata).length * 80 - margin.top - margin.bottom;

    var innerTranslate = "translate(" + margin.left + ", 0)";

    var grid = d3.range(maxYValue).map(function (i) {
        return {'x1': 0, 'y1': 0, 'x2': 0, 'y2': height};
    });

    var categoryValues = [];
    $.each(chartdata, function (i, d) {
        var category = d.category;
        var averageValues = [];
        $.each(d.studyResults, function (i, d) {
            averageValues.push(d.averageValue);
        });
        categoryValues.push({
            "categoryName": category,
            "averageValue": average(averageValues)
        });
    });

    console.log(categoryValues);
    var x = d3.scaleLinear()
            .domain([0, maxYValue])
            .range([0, width]);

    var y = d3.scaleBand()
            .domain(categoryValues.map(function (d) {
                return d.categoryName;
            }))
            .range([0, height])
            .padding(0.5)
            .paddingOuter(0.5);

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

    var bars = canvas.append("g").attr("class", "bars");

    bars.attr("transform", innerTranslate);

    var rects = bars.selectAll(".chart-bar")
            .data(categoryValues)
            .enter()
            .append("rect")
            .attr("class", "chart-bar")
            .attr("id", function (d) {
                return d.categoryName;
            });

    rects
            .attr("width", function () {
                return 0;
            })
            .attr("height", y.bandwidth())
            .attr("x", 0)
            .attr("y", function (d) {
                return y(d.categoryName);
            })
            .attr("class", "chart-bar");
    rects
            .on("mouseover", function (d) {
                var txt = canvas.append("text")
                        .text(d.averageValue)
                        .attr("class", "chart-tip");
                txt.attr("x", x(d.averageValue) / 2 + margin.left - txt.node().getBBox().width / 2);
                txt.attr("y", y(d.categoryName) + y.bandwidth() / 2 + txt.node().getBBox().height / 4);
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

function average(array) {
    var sum = 0;
    for (var i = 0; i < array.length; ++i) {
        sum += array[i];
    }
    return sum / array.length;
}