'use strict';

var myApp = angular.module('confy', ['ng-admin']);

// declare a function to run when the module bootstraps (during the 'config' phase)
myApp.config(['NgAdminConfigurationProvider', function (nga) {
  var admin = nga.application('Confy Admin')
       .baseApiUrl('api/'); // main API endpoint

  var speaker = nga.entity('speakers');
  speaker.listView()
    .fields([
        nga.field('surname'),
        nga.field('firstName')
    ])
    .listActions(['edit', 'delete']);
  speaker.creationView().fields([
     nga.field('surname'),
     nga.field('firstName')
  ]);
  speaker.editionView().fields(speaker.creationView().fields());
  admin.addEntity(speaker);

  var talk = nga.entity('talks');
  talk.listView()
    .fields([
      nga.field('title'),
      nga.field('event'),
      nga.field('dateHeld', 'date'),
      nga.field('speakerId', 'reference')
        .label('Speaker')
        .targetEntity(speaker)
        .targetField(nga.field('surname'))
    ])
    .listActions([
      'edit',
      'delete'
    ]);
  talk.creationView().fields([
    nga.field('title'),
    nga.field('event'),
    nga.field('dateHeld', 'date'),
    nga.field('speakerId', 'reference')
      .label('Speaker')
      .targetEntity(speaker)
      .targetField(nga.field('surname'))
  ]);
  talk.editionView().fields(talk.creationView().fields());
  admin.addEntity(talk);

  nga.configure(admin);
}]);