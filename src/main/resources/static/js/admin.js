'use strict';

var myApp = angular.module('confy', ['ng-admin']);
// declare a function to run when the module bootstraps (during the 'config' phase)
myApp.config(['NgAdminConfigurationProvider', function (nga) {
   var admin = nga.application('My First Admin')
         .baseApiUrl('/api/'); // main API endpoint
   var speaker = nga.entity('speakers');
   speaker.listView().fields([
       nga.field('surname').isDetailLink(true),
       nga.field('firstName')
   ]);
   speaker.creationView().fields([
       nga.field('surname'),
       nga.field('firstName')
   ]);
   console.log(speaker.creationView().fields());
   speaker.editionView().fields(speaker.creationView().fields());
   console.log(speaker.editionView().fields());
   admin.addEntity(speaker);

   nga.configure(admin);
}]);