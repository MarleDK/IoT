var bleno = require('bleno')
var getConnectionChar = require('./blenoGetConnectionPuplicKeyCharacteristic')



var PrimaryService = bleno.PrimaryService;


function BatteryService() {
    BatteryService.super_.call(this, {
        uuid: 'fffffffffffffffffffffffffffffff0', // or 'fff0' for 16-bit
        characteristics: [
            // see Characteristic for data type
            getConnectionChar
        ]
    });
}


module.exports = BatteryService;
