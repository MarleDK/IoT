var util = require('util')

var bleno = require('bleno')
BlenoCharacteristic = bleno.Characteristic

fs = require('fs')

function encrypt(key) {
  return (5*key)
}


var Characteristic = function() {
  Characteristic.super_.call(this,{
    uuid : '00007ab1-0000-1000-8000-00805f9b34fb', // or 'fff1' for 16-bit
    properties : [ 'read' ], // can be a combination of 'read', 'write', 'writeWithoutResponse', 'notify', 'indicate'
    onReadRequest : function (offset,callback){
        console.log("read requested")
        var publicKey = Math.round(Math.pow(10,10)*Math.random());
        console.log("new public key: " + publicKey)
        var privateKey = encrypt(publicKey);
        console.log("new private key: " + privateKey)
        fs.appendFile('./DB', privateKey+'\n')
        console.log(offset)
        callback(this.RESULT_SUCCESS, new Buffer([publicKey.toString]) )
      },
 // optional read request handler, function(offset, callback) { ... }
    secure : [],
    descriptors : []
  })
};

util.inherits(Characteristic,BlenoCharacteristic)

module.exports = Characteristic;
