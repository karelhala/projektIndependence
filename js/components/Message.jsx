define(['react'], function(React) {

  var Message = React.createClass({
    render: function(){
      // if (this.props.userId == userId)
      // {
      //   return(
      //     <div className="message my-message">
      //       {this.props.text}
      //     </div>
      //   );
      // }

      return (
        <div className="message">
          {this.props.text}
        </div>
      );
    }
  });

  return Message;
});
