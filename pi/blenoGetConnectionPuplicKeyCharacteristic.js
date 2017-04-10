var bleno = require('bleno')
var Characteristic = bleno.Characteristic;
fs = require('fs')

function encrypt(key) {
  return (5*key)
}

function readRequestFunction(offset,callback){
  var publicKey = Math.pow(10,10)*Math.random();
  var privateKey = encrypt(publicKey);
  fs.appendFile('./DB', privateKey+'\n')
  callback(this.RESULT_SUCCESS, new Buffer([publicKey]) )
}

class Characteristic{
  constructor(){
    this.uuid: 'fffffffffffffffffffffffffffffff1', // or 'fff1' for 16-bit
    this.properties: [ 'read' ], // can be a combination of 'read', 'write', 'writeWithoutResponse', 'notify', 'indicate'
    this.onReadRequest: readRequestFunction, // optional read request handler, function(offset, callback) { ... }

  }
};

module.exports = Characteristic;
