const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin')
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const CopyPlugin = require("copy-webpack-plugin");

const pages = ['index', 'detail', 'new-model'];

module.exports = { 
	mode: "none",
	entry: pages.reduce((entries, page) => {
		entries[page] = `./src/js/${page}.js`;
		return entries;
	}, {}),
	output: {
		filename: '[name].bundle.js',
		path: path.resolve(__dirname, 'dist'),
		clean: true,
		publicPath: "/"
	},
	devServer: { 
		historyApiFallback: {
			rewrites: pages.map(page => ({
			  from: new RegExp(`^/${page}`),
			  to: `/${page}.html`,
			})),
		},
		static: {
			directory: path.join(__dirname, 'dist')
		},
		port: 9000,
	},
	module: { 
		rules: [{ 
				test: /\.css$/, 
				use: [MiniCssExtractPlugin.loader, "css-loader"],
			},
			{ 
				test: /\.js$/, 
				exclude: /node_modules/, 
				use: { 
					loader: "babel-loader", 
					options: { 
						presets: [ "@babel/preset-env", ] 
					} 
				},
			},
			{
				test: /\.html$/,
				use: ['html-loader'],
			}, 
			{
				test: /\.(png|svg|jpg|gif)$/,
				type: "asset/resource",
				generator: {
				  filename: "assets/[name][ext]"
				}
			},
		] 
	},
	plugins: [
		new MiniCssExtractPlugin(),
		...pages.map(page => (
		  new HtmlWebpackPlugin({
			template: `./src/pages/${page}.html`,
			filename: `${page}.html`,
			chunks: [page],
		  })
		)),
		new CopyPlugin({
			patterns: [
			  { from: "assets", to: "assets" },
			],
    	}),
	],
}