var Project = React.createClass({
    rawMarkup: function() {
        var md = new Remarkable();
        var rawMarkup = md.render(this.props.children.toString());
        return { __html: rawMarkup };
    },

    render: function() {
        return (
            <div className="comment">
            <h2 className="commentAuthor">
            {this.props.author}
    </h2>
        <span dangerouslySetInnerHTML={this.rawMarkup()} />
        </div>
    );
    }
});

var ProjectBox = React.createClass({
    loadCommentsFromServer: function() {

    },
    getInitialState: function() {
        return {data: []};
    },
    componentDidMount: function() {
        this.loadCommentsFromServer();
        setInterval(this.loadCommentsFromServer, this.props.pollInterval);
    },
    render: function() {
        return (
            <div className="projectBox">
            <Project data={this.state.data} />
            </div>
    );
    }
});

ReactDOM.render(
<CommentBox url="/api/comments" pollInterval={2000} />,
document.getElementById('contentproject')
);