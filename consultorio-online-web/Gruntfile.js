module.exports = function(grunt) {
	var jsVendor = [
                'bower_components/jquery/dist/jquery.js',
				'bower_components/jquery-ui/jquery-ui.js',
				'bower_components/api-check/dist/api-check.js',
				'bower_components/bootstrap/dist/js/bootstrap.js',
                'bower_components/angular/angular.js',
                'bower_components/angular-ui-router/release/angular-ui-router.js',
				'bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
				'bower_components/angular-formly/dist/formly.js',
				'bower_components/angular-formly-templates-bootstrap/dist/angular-formly-templates-bootstrap.js'

	];
	
	var cssVendor = [
                'bower_components/jquery-ui/themes/base/jquery-ui.css',
				'bower_components/bootstrap/dist/css/bootstrap.css'

	];

	
	grunt.initConfig({
		pkg : grunt.file.readJSON('package.json'),
		env:{
			dev:{
				'conf': {
					dist: 'src/test/webapp/',
					src: 'src/main/ui/',
					configJS:'src/main/ui/config/dev.js'
				}
			},
			production:{
				'conf': {
					dist: 'src/main/webapp/',
					src: 'src/main/ui/',
					configJS:'src/main/ui/config/production.js'
				}
			}
		},
		connect : {
			server : {
				options : {
					port : 9001,
					base : './<%= conf.dist %>',
					livereload: true
				}
			}
		},
		jshint: {
			all: ['Gruntfile.js', '<%=conf.src%>**/*.js']
		},
		csslint: {
		  all: {
			options: {
			  import: 2
			},
			src: ['<%= conf.src %>**/*.css']
		  }
		},
		watch: {
			css: {
				files: ['<%=conf.src%>**/*.css'],
				tasks: ['buildCSS'],
				options: {
					interrupt: true
				}
			},
			js: {
				files: ['<%=conf.src%>**/*.js'],
				tasks: ['buildJS'],
				options: {
					interrupt: true
				}
			},
			sass: {
				files: ['<%=conf.src%>**/*.scss'],
				tasks: ['buildSass'],
				options: {
					interrupt: true
				}
			},
			resources: {
				files: ['<%=conf.src%>**/*.html'],
				tasks: ['copy:resources','htmlmin:build'],
				options: {
					interrupt: true
				}
			},
			vendor: {
				files: jsVendor,
				tasks: ['concat:vendor'],
				options: {
					interrupt: true
				}
			}
		},
		clean: {
		  build: {
			src: ['<%=conf.dist%>']
		  }
		},
		copy: {
		  resources: {
			cwd: '<%=conf.src%>',
			src: [ '**', '!**/*.scss', '!**/*.js', '!**/*.css', '!ajax/**', '!config/**' ],
			dest: '<%=conf.dist%>',
			expand: true
		  },
		  css:{
			cwd: '<%=conf.src%>',
			src: [ '**/*.css' ],
			dest: '<%=conf.dist%>',
			expand: true
		  },
		  icons:{
			cwd: 'bower_components/bootstrap/fonts/',
			src: [ '*' ],
			dest: '<%=conf.dist%>/fonts/',
			expand: true
		  },
		  maps:{
			cwd: 'bower_components/bootstrap/dist/css/',
			src: [ 'bootstrap.css.map' ],
			dest: '<%=conf.dist%>/css/',
			expand: true
		  }
		},
		htmlmin: {                                     
			build: {                                      
			  options: {                                
				removeComments: true,
				collapseWhitespace: false
			  },
			  files: {                                  
				'<%=conf.dist%>index.html': '<%=conf.src%>index.html'
			  }
			}
		},
		sass: {
			build: {
				options: {
					style: 'expanded'
				},
				files: [{
					expand: true,
					cwd: '<%=conf.src%>',
					src: ['**/*.scss'],
					dest: '<%=conf.dist%>',
					ext: '.css'
				}]
			}
		},
		concat: {
		  vendor:{
				src : jsVendor,
                dest : '<%=conf.dist%>js/vendor.js'
		  },
		  cssVendor:{
				src : cssVendor,
                dest : '<%=conf.dist%>css/vendor.css'
		  },
		  js: {
			files : {
				'<%=conf.dist%>js/app.js' : [  '<%=conf.src%>js/app.js',
						'<%=conf.src%>js/*.js' ],
				'<%=conf.dist%>config.js' : '<%=conf.configJS%>'
			}
		  }
		},
		cssmin: {
		  target: {
			files: [{
			  expand: true,
			  cwd: '<%= conf.dist %>',
			  src: ['**/*.css', '!*.min.css'],
			  dest: '<%= conf.dist %>',
			  ext: '.min.css'
			}]
		  }
		},
		uglify: {
			options : {
				mangle : {
					except : [ 'jQuery' ]
				}
			},
			dist: {
				files: {
					'<%= conf.dist %>js/app.min.js': [ '<%=conf.dist%>js/app.js' ]
				}
			}
		 }
		//}
	});
	

	grunt.loadNpmTasks('grunt-contrib-connect');
	grunt.loadNpmTasks('grunt-contrib-concat');
	grunt.loadNpmTasks('grunt-contrib-watch');
	grunt.loadNpmTasks('grunt-contrib-jshint');
	grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-contrib-copy');
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-contrib-cssmin');
	grunt.loadNpmTasks('grunt-contrib-sass');
	grunt.loadNpmTasks('grunt-contrib-csslint');
	grunt.loadNpmTasks('grunt-contrib-htmlmin');


	grunt.registerMultiTask('env', 'Set environment', function(){
		grunt.config('conf',this.data.conf);
	});

	grunt.registerTask('buildCSS', [ 'csslint', 'copy:css','cssmin:target' ]);
	grunt.registerTask('buildSass', ['sass:build','cssmin:target' ]);
	grunt.registerTask('buildJS', ['jshint:all', 'concat:js','uglify' ]);
	grunt.registerTask('buildVendor', ['concat:cssVendor','concat:vendor', 'copy:icons', 'copy:maps' ]);
	
	
	grunt.registerTask('build', ['env:dev','clean','buildVendor','copy:resources','htmlmin:build','buildSass','buildCSS','buildJS']);
	grunt.registerTask('default', ['env:production','buildVendor','copy:resources','htmlmin:build','buildSass','buildCSS','buildJS']);
	grunt.registerTask('serve', "Serve your app", ['build','connect:server', 'watch' ]);
};
