var bleno = require('bleno')
var getConnectionPuplicKeyCharacteristic = require('./blenoGetConnectionPuplicKeyCharacteristic')

class ConnectionService{
    constructor(){
        this.uuid = 'fffffffffffffffffffffffffffffff0', // or 'fff0' for 16-bit
        this.characteristics = [
            // see Characteristic for data type
            new getConnectionPuplicKeyCharacteristic()
        ]
    }
});


module.exports = ConnectionService;
