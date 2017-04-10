var Characteristic = bleno.Characteristic;
fs = require('fs')

encrypt(key) = {return 5*key}

readRequestFunction = function(offset,callback){
  var publicKey = Math.pow(10,10)*Math.random()
  var privateKey encrypt(publicKey)
  fs.appendFile('./DB', privateKey+'\n')
  callback(this.RESULT_SUCCESS, new Buffer([publicKey]) )
}

var characteristic = new Characteristic({
    uuid: 'fffffffffffffffffffffffffffffff1', // or 'fff1' for 16-bit
    properties: [ 'read' ], // can be a combination of 'read', 'write', 'writeWithoutResponse', 'notify', 'indicate'
    value: null, // optional static value, must be of type Buffer - for read only characteristics
    descriptors: [
        // see Descriptor for data type
    ],
    onReadRequest: readRequestFunction, // optional read request handler, function(offset, callback) { ... }
    onWriteRequest: null, // optional write request handler, function(data, offset, withoutResponse, callback) { ...}
    onSubscribe: null, // optional notify/indicate subscribe handler, function(maxValueSize, updateValueCallback) { ...}
    onUnsubscribe: null, // optional notify/indicate unsubscribe handler, function() { ...}
    onNotify: null, // optional notify sent handler, function() { ...}
    onIndicate: null // optional indicate confirmation received handler, function() { ...}
});
