var ProductRow = React.createClass({
    render: function () {
        var tags = "";
        this.props.product.tags.forEach(function (tag) {
            tags += tag.name + " ";
        });
        var pricePointsStr = "";
        var pricePointCount = this.props.product.pricePoints.length;
        this.props.product.pricePoints.forEach(function (pricePoint, i) {
            if (i == pricePointCount - 1) {
                pricePointsStr += pricePoint.currencyCode + " " + pricePoint.value;
            } else {
                pricePointsStr += pricePoint.currencyCode + " " + pricePoint.value + ",";
            }
        });
        var that = this;
        var onProductEdit = function () {
            that.props.onProductEdit(that.props.product.id);
        }
        return (
            <tr>
                <td>{this.props.product.name}</td>
                <td>{this.props.product.description}</td>
                <td>{tags}</td>
                <td>{this.props.product.price.currencyCode} {this.props.product.price.value}</td>
                <td>{pricePointsStr}</td>
                <td><a href="#" onClick={onProductEdit}>Edit</a></td>
            </tr>
        );
    }
});

var ProductTable = React.createClass({
    onProductEdit: function (productId) {
        this.props.onProductEdit(productId);
    },
    render: function () {
        var that = this;
        var rows = this.props.data.list.map(function (product) {
            return (
                <ProductRow product={product} key={product.name} onProductEdit={that.onProductEdit}/>
            );
        });
        return (
            <table>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Tags</th>
                    <th>Price</th>
                    <th>Price points</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>{rows}</tbody>
            </table>
        );
    }
});

var ProductForm = React.createClass({
    getInitialState: function () {
        return {name: '', description: '', tags: '', price: {value: 0.0, currencyCode: "USD"}, pricePoints: []};
    },
    handleNameChange: function (e) {
        this.setState({name: e.target.value});
    },
    handleDescriptionChange: function (e) {
        this.setState({description: e.target.value});
    },
    handleTagsChange: function (e) {
        this.setState({tags: e.target.value});
    },
    handlePriceValueChange: function (e) {
        var price = this.state.price;
        price.value = e.target.value;
        this.setState(price);
    },
    handlePriceCurrencyChange: function (e) {
        var price = this.state.price;
        price.currencyCode = e.target.value;
        this.setState(price);
    },
    handleSubmit: function (e) {
        e.preventDefault();
        var id = this.state.id;
        var name = this.state.name.trim();
        var description = this.state.description.trim();
        var tagNames = this.state.tags.trim();
        var tags = [];
        if (!isEmpty(tagNames)) {
            tagNames = tagNames.split(" ");
            tagNames.forEach(function (tagName) {
                var tag = {};
                tag.name = tagName.trim();
                tags.push(tag);
            });
        }

        var price = this.state.price;

        if (id) {
            this.props.onProductUpdate({id: id, name: name, description: description, tags: tags, price: price});
        } else {
            this.props.onProductCreate({name: name, description: description, tags: tags, price: price});
        }

        this.setState({id:undefined, name: '', description: '', tags: '', price: {value: 0.0, currencyCode: "USD"}});
    },
    onProductEdit: function (product) {
        this.setState({id: product.id});
        this.setState({name: product.name});
        this.setState({description: product.description});
        var tags = '';
        product.tags.forEach(function (tag) {
            tags += tag.name + ' ';
        });
        this.setState({tags: tags});
        this.setState({price: product.price});
    },
    render: function () {
        return (
            <form className="productForm" onSubmit={this.handleSubmit}>
                <input
                    type="text"
                    placeholder="Name"
                    value={this.state.name}
                    onChange={this.handleNameChange}
                />
                <input
                    type="text"
                    placeholder="Description"
                    value={this.state.description}
                    onChange={this.handleDescriptionChange}
                />
                <input
                    type="text"
                    placeholder="Tags e.g. Electronics"
                    value={this.state.tags}
                    onChange={this.handleTagsChange}
                />
                <input
                    type="text"
                    placeholder="Price"
                    value={this.state.price.value}
                    onChange={this.handlePriceValueChange}
                />
                <select placeholder="Currency"
                        value={this.state.price.currencyCode}
                        onChange={this.handlePriceCurrencyChange}>
                    <option value="USD">USD</option>
                    <option value="GBP">GBP</option>
                </select>
                <input type="submit" value="Save"/>
            </form>
        );
    }
});

var ProductBox = React.createClass({
    loadProductsFromServer: function () {
        $.ajax({
            url: this.props.url,
            dataType: 'json',
            cache: false,
            success: function (data) {
                this.setState({data: data});
            }.bind(this),
            error: function (xhr) {
                this.handleError(xhr);
            }.bind(this)
        });
    },
    handleProductCreate: function (product) {
        $.ajax({
            url: this.props.url,
            type: 'POST',
            data: JSON.stringify(product),
            contentType: 'application/json',
            dataType: 'json',
            success: function () {
                this.loadProductsFromServer();
            }.bind(this),
            error: function (xhr) {
                this.handleError(xhr);
            }.bind(this)
        });
    },
    handleProductUpdate: function (product) {
        $.ajax({
            url: this.props.url + product.id,
            type: 'PUT',
            data: JSON.stringify(product),
            contentType: 'application/json',
            dataType: 'json',
            success: function () {
                this.loadProductsFromServer();
            }.bind(this),
            error: function (xhr) {
                this.handleError(xhr);
            }.bind(this)
        });
    },
    handleProductEdit: function (productId) {
        $.ajax({
            url: this.props.url + productId,
            dataType: 'json',
            cache: false,
            success: function (data) {
                this.refs['productForm'].onProductEdit(data);
            }.bind(this),
            error: function (xhr) {
                this.handleError(xhr);
            }.bind(this)
        });
    },
    handleError: function (err) {
        if (err.responseJSON.key) {
            alert(err.responseJSON.key);
        }
        console.error(err.responseText);
    },
    getInitialState: function () {
        return {data: {count: 0, list: []}};
    },
    componentDidMount: function () {
        this.loadProductsFromServer();
    },
    render: function () {
        return (
            <div className="productBox">
                <h1>Product Demo</h1>
                <ProductForm ref="productForm" onProductCreate={this.handleProductCreate}
                             onProductUpdate={this.handleProductUpdate}/>
                <ProductTable data={this.state.data} onProductEdit={this.handleProductEdit}/>
            </div>
        );
    }
});

function isEmpty(str) {
    return (!str || 0 === str.length);
}

ReactDOM.render(
    <ProductBox url="/products/"/>,
    document.getElementById('content')
);
