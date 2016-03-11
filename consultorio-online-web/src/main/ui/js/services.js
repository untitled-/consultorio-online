var app = angular.module('main');

app.service('PatientService',['$http','restApi',function($http, restApi){
	return{
		getPromiseAllPatients:function(handler,errorHandler){
			  var responsePromise = $http.get(restApi.getPatients);
              return responsePromise;
		},
		getPerson:function(){
			return {
				name:'Jane',
				lastName:'Doe',
				dob:'19 de Junio, 1988',
				photoUrl:'images/face.jpg',
				status:'En tratamiento', //Alta, En tratamiento, Otros
				appointments:{
					next:{
						date:new Date()
					},
					previous:{
						date:new Date(),
						diagnostic:{
							description:'Lorem ipsum',
							prescription:{
								number:'10000001',
								drugs:[{
									name:'Paracetamol',
									quantity:'3 pastillas'
								}]
							}
						}
					}
				},
				medicalHistory:{
					childhood:['Sarampión y bronquiectasias','Parotiditis','Rubéola'],
					medical:['Gastritis'],
					alergies:['Alergias inhalatorias','Alergias cutáneas','Alergias a fármacos'],
					quirugical:['Apendicitis']
				}
			};
		}
	};
}]);


app.service('UserService',function(){
	return{
		getLoggedUser:function(){
			return {
				name: 'Pedro',
				lastName: 'Marquez'
			};
		}
	};
});