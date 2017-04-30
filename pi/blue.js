var bleno = require('bleno')
var BlenoPrimaryService = bleno.PrimaryService
var PuplicKeyCharacteristic = require('./blenoGetConnectionPuplicKeyCharacteristic')
//var WriteCharacteristic = require('./blenoWriteToPiCharacteristic')

var primaryService = new BlenoPrimaryService({
  uuid : '00007ab0-0000-1000-8000-00805f9b34fb', // or 'fff0' for 16-bit
  characteristics : [
      // see Characteristic for data type
      new PuplicKeyCharacteristic()
  ]
});

bleno.on('stateChange', function(state) {
  console.log('on -> stateChange: ' + state);

  if (state === 'poweredOn') {
    bleno.startAdvertising('Raspi', [primaryService.uuid]);
  } else {
    bleno.stopAdvertising();
  }
});

bleno.on('advertisingStart', function(error) {
  console.log('on -> advertisingStart: ' + (error ? 'error ' + error : 'success'));

  if (!error) {
    bleno.setServices([primaryService], function(error){
      console.log('setServices: '  + (error ? 'error ' + error : 'success'));
      console.log(primaryService.uuid)
    });
  }
});

