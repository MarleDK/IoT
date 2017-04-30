//blenoWriteToPiCharacteristic.js

var util = require('util')
var db = require('./db')
var bleno = require('bleno')
BlenoCharacteristic = bleno.Characteristic

fs = require('fs')

function getPrivateKey(string) {
    return [string.slice(0,string.indexOf(":")),string.slice(string.indexOf(":"))]
}


var Characteristic = function() {
  Characteristic.super_.call(this,{
    uuid : '00007ab2-0000-1000-8000-00805f9b34fb', // or 'fff1' for 16-bit
    properties : [ 'write' ], // can be a combination of 'read', 'write', 'writeWithoutResponse', 'notify', 'indicate'
    onWriteRequest : function(data, offset, withoutResponse, callback){
        console.log("write requested")
        req = String.fromCharCode.apply(null,data)
        console.log("request data: " + req)
        var temp = getPrivateKey(req)
        privateKey = parseInt(temp[0])
        message = temp[1]
        console.log("private key: " + privateKey)
        if(db.privateKeys.indexOf(privateKey) > -1){
            console.log("---------" + message + "---------")
            callback(this.RESULT_SUCCESS)
        } else {
            console.log("Permission denied")
            callback(this.RESULT_SUCCESS)
        }
      },
 // optional read request handler, function(offset, callback) { ... }
    secure : [],
    descriptors : []
  })
};

util.inherits(Characteristic,BlenoCharacteristic)

module.exports = Characteristic;
