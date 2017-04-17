var bleno = require('bleno')
fs = require('fs')

function encrypt(key) {
  return (5*key)
}


class Characteristic{
  constructor(){
    this.uuid = '00007ab1-0000-1000-8000-00805f9b34fb', // or 'fff1' for 16-bit
    this.properties = [ 'read' ], // can be a combination of 'read', 'write', 'writeWithoutResponse', 'notify', 'indicate'
    this.onReadRequest = function (offset,callback){
        console.log("read requested")
        var publicKey = Math.pow(10,10)*Math.random();
        var privateKey = encrypt(publicKey);
        fs.appendFile('./DB', privateKey+'\n')
        callback(this.RESULT_SUCCESS, new Buffer([publicKey]) )
      }
 // optional read request handler, function(offset, callback) { ... }
    this.secure = []
    this.descriptors = []
  }
};

module.exports = Characteristic;
