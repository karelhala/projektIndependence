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

      //JSON PARSING LOGIC WILL BE HERE

      //this.dispatch();
    }

  });

});
