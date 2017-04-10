var bleno = require('bleno')
var getConnectionChar = require('./blenoGetConnectionPuplicKeyCharacteristic')



var PrimaryService = bleno.PrimaryService;


class BatteryService() {
    uuid: 'fffffffffffffffffffffffffffffff0', // or 'fff0' for 16-bit
    characteristics: [
        // see Characteristic for data type
        new getConnectionChar
    ]
});


module.exports = BatteryService;
