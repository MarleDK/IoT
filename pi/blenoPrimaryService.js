var bleno = require('bleno')
var getConnectionChar = require('./blenoGetConnectionPuplicKeyCharacteristic')

var PrimaryService = bleno.PrimaryService;

var primaryService = new PrimaryService({
    uuid: 'fffffffffffffffffffffffffffffff0', // or 'fff0' for 16-bit
    characteristics: [
        // see Characteristic for data type
        characteristic
    ]
});
