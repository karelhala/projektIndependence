define(["estedispatcher", "objectassign"], function(EsteDispatcher, ObjectAssign) {

  return ObjectAssign(new este.Dispatcher(), {

    /**
     * @param  {object} action The data coming from the view.
     */
    handleViewAction: function(action) {
      	this.dispatch(action.type, action.data);
    },

     /**
     * @param  {object} action The data coming from the server.
     */
    handleServerAction: function(action) {
//		 webSocket.onmessage = function(event){
//			 var data = JSON.parse(event.data);
//			 var type = data.type;
//			 console.log(data);
//			 console.log(type);
//		 }
    }

  });

});
