angular.module('CommunityApp', [])
   .controller('CommunityController', function($scope, $http) {

    console.log("before register");
    $scope.member = {};
    $scope.currentUser;
    $scope.returningUser = {};

        $scope.register = function(firstName, lastName, email, password, streetAddress) {
            console.log("In register function in ng controller");

            //Make a container
            var newMember = {
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password,
                streetAddress: streetAddress
            }

            console.log("Container we're about to send: " + newMember.firstName + " " + newMember.lastName + " " + newMember.email + " " + newMember.password + " " + newMember.streetAddress);

            $http.post("/register.json", newMember)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");
                        $scope.loginContainer = response.data;
                        $scope.member = {};
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

    console.log("before login");

        $scope.login = function(loginEmail, loginPassword) {
            console.log("In login function in ng controller");

            //Make a container
            var returningUser = {
                email: loginEmail,
                password: loginPassword
            }

            console.log("Container we're about to send: " + returningUser.email + " " + returningUser.password);

            $http.post("/login.json", returningUser)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");
                        $scope.loginContainerForLogin = response.data;
                        console.log("Data added after logincontainer user")
                        $scope.currentUser = response.data; //
                        console.log("Data added after current user")
//                        $scope.returningUser = {};
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

   console.log("before sendPost");

        $scope.sendPost = function (newPostDate, newPostTitle, bodyOfNewPost) {
             console.log("In sendPost function in ng controller");

             //Make a container
             var postContainer = {
                  date: newPostDate,
                  title: newPostTitle,
                  body: bodyOfNewPost,
                  member: $scope.currentUser
             }

             console.log("Container we're about to send: " + postContainer.date + " " + postContainer.title + " " + postContainer.body);

//             $http.post("/createPost/" + $scope.currentUser.responseMember.id + ".json", postContainer)
              $http.post("/createPost.json", postContainer)

                  .then(
                     function successCallback(response) {
                         console.log(response.data);
                         console.log("Adding data to scope");
                         // Returns container with error or user
                         $scope.allPosts = response.data;
                     },
                     function errorCallback(response) {
                         console.log("Unable to get data...");
                     });
        };


    console.log("before createOrganization")

                $scope.createOrganization = function (newOrganizationName) {
                     console.log("In createOrganization function in ng controller");

                     //Make a container
                     var organizationContainer = {
                          name: newOrganizationName,
                          }

                     console.log("Container we're about to send: " + organizationContainer.name);

                      $http.post("/createOrganization.json", organizationContainer)

                          .then(
                             function successCallback(response) {
                                 console.log(response.data);
                                 console.log("Adding data to scope");
                                 // Returns container with error or user
                                 $scope.allOrganizations = response.data;
                             },
                             function errorCallback(response) {
                                 console.log("Unable to get data...");
                             });
                };



    console.log("before createNewEvent");

        $scope.createNewEvent = function(newEventName, newEventDate, newEventLocation, newEventInformation, newEventOrganizer) {
             console.log("In createNewEvent function in ng controller");

             //Make a container
             var newEvent = {
                 name: newEventName,
                 date: newEventDate,
                 location: newEventLocation,
                 information: newEventInformation,
                 organizer: newEventOrganizer
             }

             console.log("Container we're about to send: " + newEvent.newEventName + " " + newEvent.newEventDate + " " + newEvent.newEventLocation + " " + newEvent.newEventInformation + " " + newEvent.newEventOrganizer);

             $http.post("/createEvent.json", newEvent)
                 .then(
                     function successCallback(response) {
                         console.log(response.data);
                         console.log("Adding data to scope");
                         $scope.newEventContainer = response.data;
                         $scope.allEvents = response.data;
                     },
                     function errorCallback(response) {
                         console.log("Unable to get data...");
                     });
         };

    console.log("before joinEvent");

//        not listed in our endpoints
        $scope.joinEvent = function(myMemberId, eventIWantToJoinId) {
             console.log("In joinEvent function in ng controller");

             //Make a container
             var newUserEvent = {
                  memberId: myMemberId,
                  eventId: eventIWantToJoinId
             }

             console.log("Container we're about to send: " + newUserEvent.myMemberId + " " + newUserEvent.eventIWantToJoinId);

             $http.post("/joinEvent.json", newUserEvent)
                  .then(
                     function successCallback(response) {
                         console.log(response.data);
                         console.log("Adding data to scope");
                         // Returns a list of attendees
                         $scope.eventAttendees = response.data;
                     },
                     function errorCallback(response) {
                         console.log("Unable to get data...");
                     });
        };



    console.log("Page loaded!");

    });