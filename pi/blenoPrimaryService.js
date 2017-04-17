var bleno = require('bleno')
var getConnectionPuplicKeyCharacteristic = require('./blenoGetConnectionPuplicKeyCharacteristic')

class ConnectionService{
    constructor(){
        this.uuid = '00007ab0-0000-1000-8000-00805f9b34fb', // or 'fff0' for 16-bit
        this.characteristics = [
            // see Characteristic for data type
            new getConnectionPuplicKeyCharacteristic()
        ]
    }
}


module.exports = ConnectionService;
