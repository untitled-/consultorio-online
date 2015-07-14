module.exports = function(grunt) {
	var SRC_DIR = 'src/';

	var BUILD_DIR = 'dist/';
	var BUILD_DIR_JS = BUILD_DIR + 'js/';
	var BUILD_DIR_CSS = BUILD_DIR + 'css/';
	
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
		watch: {
		  files: {
			files: [ '**/html/**'],
			tasks: ['copy'],
			options: {
			  livereload: true,
			},
		  },
		  javascript: {
			  files: [SRC_DIR + '**/js/*.js'],
			  tasks: ['uglify'],
			  options: {
				// Start a live reload server on the default port 35729
				livereload: true,
			  },
			},
		  sass: {
			  // We watch and compile sass files as normal but don't live reload here
			  files: [SRC_DIR + '**/css/*.scss'],
			  tasks: ['buildStyles'],
			},
		},
		jshint : {
			files : [ 'Gruntfile.js', 'js/main.js' ],
			options : {
				jshintrc : '.jshintrc',
				globals : {
					jQuery : true
				}
			}
		},
		clean : {
			build : {
				src : [ BUILD_DIR ]
			},
			scripts : {
				src : [ BUILD_DIR_JS + '*.js' ]
			}
		},
		copy : {
			build : {
				cwd : SRC_DIR,
				src : [ '**/html/**'],
				dest : BUILD_DIR,
				expand : true
			}
		},
		uglify : {
			min: {
				files: grunt.file.expandMapping(['**/*.js', '**/*.js'], BUILD_DIR_JS, {
					rename: function(destBase, destPath) {
						return destBase+destPath.replace('.js', '.min.js');
					}
				})
			}
		},
		cssmin : {
			compress : {
				files : {
					"temp/css/main.min.css" : [ SRC_DIR + 'acssp-core/css/*.css' ]
				}
			}
		},
		sass: {                              // Task
			dist: {
            options: {
                style: 'compressed'
            },
            files: [{
                expand: true,
                cwd: SRC_DIR + 'acssp-core/css',
                src: ['*.scss'],
                dest: 'temp/css',
                ext: '.css'
            }]
        }
		  },
		  concat: {
			  css: {
				src: ['temp/css/*.css'],
				dest: 'dist/acssp-core/css/main.min.css'
			  },	
			  js: {
				files : {
					'dist/acssp-core/acssp-angular-core.js' : [ SRC_DIR + 'acssp-core/js/app.js',
							SRC_DIR + 'acssp-core/js/*.js' ],
					'dist/acssp-authentication/acssp-authentication-api.js' : [ SRC_DIR + 'acssp-authentication/js/app.js',
							SRC_DIR + 'acssp-authentication/js/*.js' ],
					'dist/acssp-messagecenter/acssp-messagecenter-api.js' : [ SRC_DIR + 'acssp-messagecenter/js/app.js',
							SRC_DIR + 'acssp-messagecenter/js/*.js' ]
				}
			  }
			}
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
	
	grunt.registerTask('clean:temp', function () {
		grunt.file.delete('temp');
	});

	grunt.registerTask('buildStyles', [ 'cssmin', 'sass', 'concat', 'clean:temp' ]);
	grunt.registerTask('default', [ 'jshint', 'clean:build', 'copy:build',
			'uglify', 'buildStyles' ]);
	
	grunt.registerTask('serve', "Serve your app", [
                       'connect:server', 'watch' ]);
};
