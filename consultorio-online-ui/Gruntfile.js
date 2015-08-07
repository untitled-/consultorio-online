module.exports = function(grunt) {
	var jsVendor = [
                'bower_components/jquery/dist/jquery.js',
                'bower_components/angular/angular.js',
	];

	
	grunt.initConfig({
		pkg : grunt.file.readJSON('package.json'),
		connect : {
			server : {
				options : {
					port : 9001,
					base : '.',
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
				tasks: ['loadconst','copy:resources','htmlmin:build'],
				options: {
					interrupt: true
				}
			},
			vendor: {
				files: jsVendor,
				tasks: ['loadconst','concat:vendor'],
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
			src: [ '**', '!**/*.scss', '!**/*.js', '!**/*.css' ],
			dest: '<%=conf.dist%>',
			expand: true
		  },
		  css:{
			cwd: '<%=conf.src%>',
			src: [ '**/*.css' ],
			dest: '<%=conf.dist%>',
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
				'<%=conf.dist%>main/index.html': '<%=conf.src%>main/index.html'
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
                dest : '<%=conf.dist%>main/js/vendor.js'
		  },
		  js: {
			files : {
				'<%=conf.dist%>main/js/app.js' : [  '<%=conf.src%>main/js/app.js',
						'<%=conf.src%>main/js/*.js' ]
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
					'<%= conf.dist %>main/js/app.min.js': [ '<%=conf.dist%>main/js/app.js' ]
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

	
	grunt.registerTask('loadconst', 'Load constants', function() {
		grunt.config('conf', {
			dist: 'dist/',
			src: 'src/',
		});
	});
	
	grunt.registerTask('buildCSS', ['loadconst', 'csslint', 'copy:css','cssmin:target' ]);
	grunt.registerTask('buildSass', ['loadconst', 'sass:build','cssmin:target' ]);
	grunt.registerTask('buildJS', ['loadconst', 'jshint:all', 'concat:js','uglify' ]);
	
	
	grunt.registerTask('default', ['loadconst','clean:build','concat:vendor','copy:resources','htmlmin:build','buildSass','buildCSS','buildJS']);
	grunt.registerTask('serve', "Serve your app", ['default','connect:server', 'watch' ]);
};
