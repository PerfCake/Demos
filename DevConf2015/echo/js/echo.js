var net = require('net');

String.prototype.reverse = function() {
    var s = "";
    var i = this.length;
    while (i>0) {
        s += this.substring(i-1,i);
        i--;
    }
    return s;
}

var server = net.createServer(function(conn) {
  console.log("Connection from " + conn.remoteAddress + " on port " + conn.remotePort);
  conn.setEncoding("utf8");
  var buffer = "";
 
  conn.on("data", function(data) {
    for(var i = 0; i <= data.length; i++) {
      var char = data.charAt(i);
      if(char == "\n") {
        conn.write(buffer.reverse() + "\n");
        buffer = "";
      } else {
        buffer += char;
      }
    }
  });
});

console.log("Started JS echo server at port 12321, hit ^C to terminate..."); 
server.listen(12321, "localhost");