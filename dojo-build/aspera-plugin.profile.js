// See http://dojotoolkit.org/reference-guide/build/index.html#build-index
var profile = {
	basePath: "../target/dojoBuild", // The path of the directory where Dojo Toolkit full source archive file is extracted to

	releaseDir: "dojo/../release",
	action: "release",
	layerOptimize: "comments",
	optimize: "comments",
	stripConsole: "warn",
	localeList: "ar,bg,ca,cs,da,de,el,es,fi,fr,he,hr,hu,it,iw,ja,kk,ko,nb,nb-no,nl,no,pl,pt,pt-br,ru,ro,sk,sl,sv,th,tr,vi,zh,zh-cn,zh-tw",

	packages: [
		{
			name: "dojo",
			location: "./dojo"
		},
		{
			name: "dijit",
			location: "./dijit"
		},
		{
			name: "dojox",
			location: "./dojox"
		},
		{
			name: "gridx",
			location: "./gridx"
		},
		{
			name: "idx",
			location: "./idx"
		},
		{
			name: "ecm",
			location: "./ecm"
		},
		{
			name: "pvr",
			location: "./pvr"
		},
		{
			name: "pvd",
			location: "./pvd"
		},
		{
			name: "aspera",
			location: "./aspera"
		}
	],

	layers: {
		"aspera/AsperaPlugin": {
			include: [
				"aspera/AsperaPlugin"
			],
			exclude: [
				"dojo/dojo",
				"dijit/dijit",
				"ecm/ecm"
			]
		}
	}

};
