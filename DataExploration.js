var svg = d3.select("#choropleth1");
var width = 960;
var height = 600;
var projection = d3.geoAlbersUsa()
.translate([width/2, height/2])    
.scale([1000]);        
var path = d3.geoPath().projection(projection);
var color = d3.range(["rgb(213,222,217)","rgb(0,128,0)"]);
var legendText = ["Reviewd", "Not reviewed"];
var div = d3.select("body").append("div")
  .attr("class", "tooltip")
  .style("opacity", 0);  

$("#btnHotelsPerState").click(function () {
    $.ajax({
        url: "DataExploration",
        type: "POST",
        data: "{}",
        success: function (hotelsPerState) {
            drawChoropleth("hotelsPerState.json")
        },
        error: function(){
            console.log("error occurred while making ajax call;")
        }
    });
});
$("#btnBookingsPerState").click(function () {
    $.ajax({
        url: "DataExploration",
        type: "POST",
        data: "{}",
        success: function (bookingsPerState) {
            drawChoropleth("bookingsPerState.json")
        },
        error: function(){
            console.log("error occurred while making ajax call;")
        }
    });
});
function drawChoropleth(fileName){
d3.queue()
.defer(d3.json, "state.topo.json")
.defer(d3.json, fileName)
.await(ready);
function ready(error, us, map_data) {
	if (error) throw error;
	for(var idx in map_data) {

		var state_name = map_data[idx].state;
		var review_count = map_data[idx].count;
		var us_len = us.objects.state.geometries.length;
		for (var j = 0; j < us_len; j++) {
			var usStateName = us.objects.state.geometries[j].properties.STUSPS10;
			if (state_name == usStateName) {
				us.objects.state.geometries[j].properties.count = review_count;
				break;
			}
		}
      }
d3.selection.prototype.moveToFront = function() {
	return this.each(function(){
	this.parentNode.appendChild(this);
	});
};
var states = topojson.feature(us, us.objects.state).features
svg.selectAll(".state")
.data(states)
.enter()
.append("path")
.attr("d", path)
.style("stroke-width", 1)
.style("fill",  function(d) {
var value = d.properties.count;
if (value) {
	return "#008000";
}
else {
      return "rgb(213,222,217)";
}
})
.on("mouseover", function(d) {
var sel = d3.select(this);
sel.moveToFront();
d3.select(this).transition().duration(300).style("opacity", 0.8);
div.transition().duration(300)
.style("opacity", 1)
div.text(d.properties.STUSPS10 + ": " + d.properties.count)
.style("left", (d3.event.pageX) + "px")
.style("top", (d3.event.pageY -30) + "px");
});
}
}
