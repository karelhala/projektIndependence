define(['react'], function(React) {

  var Message = React.createClass({
    render: function(){
      if(this.props.type == 'NOTIFICATION') {
          return(
            <div className="message">
              <i>{this.props.userId}</i>: <i> {this.props.text}</i>
            </div>
          );
      } else if (this.props.userId == userId) {
        return(
          <div className="message">
            <b>{this.props.userId}: </b>{this.props.text}
          </div>
        );
      } else {
        return (
          <div className="message">
            {this.props.userId}: {this.props.text}
          </div>
        );
      }
    }
  });

  return Message;
});
