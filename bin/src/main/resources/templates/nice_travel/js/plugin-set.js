jQuery( document ).ready( function() {
        // slider text
        var slidtext = jQuery( '#slidertext' );
        jQuery( function() {
            var slideBegin = 3000,
                transSpeed = 500,
                simple_slideshow = slidtext,
                listItems = simple_slideshow.children( '.main-text' ),
                listLen = listItems.length,
                i = 0,
                changeList = function() {
                    listItems.eq( i )
                        .fadeOut( transSpeed, function() {
                            i += 1, i === listLen && ( i = 0 ), listItems.eq( i )
                                .fadeIn( transSpeed )
                        } )
                };
            listItems.not( ':first' )
                .hide(), setInterval( changeList, slideBegin );
        } );
        // Magnific Popup img
        jQuery( '.big-img' )
            .magnificPopup( {
                delegate: 'a',
                type: 'image',
                closeOnContentClick: false,
                closeBtnInside: false,
                mainClass: 'mfp-with-zoom mfp-img-mobile',
                image: {
                    verticalFit: true,
                },
                gallery: {
                    enabled: false
                },
                zoom: {
                    enabled: true,
                    duration: 300, // don't foget to change the duration also in CSS
                    opener: function( element ) {
                        return element.find( 'img' );
                    }
                }
            } );
			
	    // Magnific Popup img
        jQuery( '.big-img-1' )
            .magnificPopup( {
                type: 'image'
            } );
			
// Magnific Popup dailymotion
$('.big-video').magnificPopup({
  type: 'iframe',
  iframe: {
    patterns: {
      dailymotion: {
        index: 'dailymotion.com',
        id: function(url) {        
            var m = url.match(/^.+dailymotion.com\/(video|hub)\/([^_]+)[^#]*(#video=([^_&]+))?/);
            if (m !== null) {
                if(m[4] !== undefined) {
                    return m[4];
                }
                return m[2];
            }
            return null;
        },
        src: 'http://www.dailymotion.com/embed/video/%id%'
      }
    }
  }
});

// Magnific Popup youtube
$('.big-youtube').magnificPopup({
    disableOn: 700,
    type: 'iframe',
    mainClass: 'mfp-with-zoom mfp-img-mobile',
    removalDelay: 0,
    preloader: false,
    fixedContentPos: false,
    iframe: {
        patterns: {
            youtube: {
                src: 'http://www.youtube.com/embed/%id%?autoplay=1&rel=0'
            }
        }
    }
});

// vimeo video
$('.vim-video').magnificPopup({
  type: 'iframe',
  iframe: {
     markup: '<div class="mfp-iframe-scaler">'+
                '<div class="mfp-close"></div>'+
                '<iframe class="mfp-iframe" frameborder="0" allowfullscreen></iframe>'+
                '<div class="mfp-title">Some caption</div>'+
              '</div>'
  },
  callbacks: {
    markupParse: function(template, values, item) {
     values.title = item.el.attr('title');
    }
  }
});

//popup video player
$('.popup-video').magnificPopup({ 
    type: 'iframe', 
    mainClass: 'mfp-fade', 
    removalDelay: 160, 
    preloader: false, 
    fixedContentPos: false, 
    iframe: { 
     markup: '<div class="mfp-iframe-scaler">'+ 
       '<div class="mfp-close"></div>'+ 
       '<iframe class="mfp-iframe" frameborder="0" allowfullscreen></iframe>'+ 
       '</div>', 

     srcAction: 'iframe_src', 
     } 
}); 
        // step work
        var jQuerycontainerstep = jQuery( '#step-text' );
        jQuerycontainerstep.isotope( {
            itemSelector: '.cont',
            filter: '.planing',
            hiddenStyle: {
                opacity: 0
            },
            visibleStyle: {
                opacity: 1
            }
        } );
        jQuery( '.filt-step' )
            .on( 'click', function( e ) {
                e.preventDefault();
                var jQuerythis = jQuery( this );
                if ( jQuerythis.hasClass( 'active' ) ) {
                    return false;
                }
                var jQueryoptionSet = jQuerythis.parents();
                jQueryoptionSet.find( '.active' )
                    .removeClass( 'active' );
                jQuerythis.addClass( 'active' );
                var selector = jQuery( this )
                    .attr( 'data-filter' );
                jQuerycontainerstep.isotope( {
                    filter: selector,
                } );
                return false;
            } );
        // service
        var jQuerycontainer = jQuery( '#services' );
        jQuerycontainer.isotope( {
            itemSelector: '.service',
            filter: '.passion',
            hiddenStyle: {
                opacity: 0
            },
            visibleStyle: {
                opacity: 1
            }
        } );
        jQuery( '.filt-serv' )
            .on( 'click', function( e ) {
                e.preventDefault();
                var jQuerythis = jQuery( this );
                if ( jQuerythis.hasClass( 'selected' ) ) {
                    return false;
                }
                var jQueryoptionSet = jQuerythis.parents();
                jQueryoptionSet.find( '.selected' )
                    .removeClass( 'selected' );
                jQuerythis.addClass( 'selected' );
                var selector = jQuery( this )
                    .attr( 'data-filter' );
                jQuerycontainer.isotope( {
                    filter: selector,
                } );
                return false;
            } );
// set mansory gallery max width
var maingall = jQuery('#w-gallery-container');
if (maingall.length) {
var masonry = new Macy({
        container: '#w-gallery-container',
        trueOrder: false,
        waitForImages: true,
        debug: true,
        columns: 3,
        breakAt: {
          1200: {
            columns: 3,
          },
          940: {
		    columns: 2,
          },
          520: {
            columns: 2,
          },
          400: {
            columns: 1
          }
        }
      });
} else {
   //nothing happen
}

        // projects width gallery
        var $Wcontainerpro = jQuery( '.w-gallery-container' );
        $Wcontainerpro.isotope( {
            itemSelector: '.w-gallery',
            filter: '*'
        } );
        jQuery( '.filt-projects-w' )
            .on( 'click', function( e ) {
                e.preventDefault();
                var $this = jQuery( this );
                if ( $this.hasClass( 'selected' ) ) {
                    return false;
                }
                var $optionSetpro = $this.parents();
                $optionSetpro.find( '.selected' )
                    .removeClass( 'selected' );
                $this.addClass( 'selected' );
                var selector = $( this )
                    .attr( 'data-project' );
                $Wcontainerpro.isotope( {
                    filter: selector,
                } );
                return false;
            });
  // layout Isotope after each image loads
  $Wcontainerpro.imagesLoaded().progress( function() {
  $Wcontainerpro.isotope('layout');
  });
  // filter galery hide
  $(".filtter-icon").on("click", function() {
  $("#filter-porto").slideToggle(600);
  $("#filter-hide").toggleClass('show');
  });
  // owlCarousel gallery
  $("#owl-testimonial").owlCarousel({
        margin:15,
        responsiveClass:true,
        responsive:{
        0:{
            items:1,
        },
        320:{
            items:1,
        },
        414:{
            items:1,
            loop:true,
        },
        800:{
            items:1,
            loop:true,
        },
        1024:{
            items:1,
            loop:true,
         }
        }
        });
        // owlCarousel gal
        $("#owl-gal").owlCarousel({
        margin:15,
        responsiveClass:true,
        responsive:{
        0:{
            items:1,
            nav:true,
            stagePadding: 0
        },
        320:{
            items:1,
            nav:true,
            stagePadding: 0
        },
        414:{
            items:1,
            nav:true,
            loop:true,
            stagePadding: 0
        },
        800:{
            items:2,
            nav:true,
            loop:true,
            stagePadding: 0
        },
        1024:{
            items:3,
            nav:true,
            loop:true,
            stagePadding: 160
         }
        },
        nav:true,
        navText : ['<i class="fa fa-angle-left" aria-hidden="true"></i>','<i class="fa fa-angle-right" aria-hidden="true"></i>']
        });
        // owlCarousel recent post
        var owl = jQuery( "#owl-post" );
        owl.owlCarousel( {
            items: 3,
            pagination: false,
            itemsDesktop: [ 1000, 2 ],
            itemsDesktopSmall: [ 900, 2 ],
            itemsTablet: [ 600, 1 ],
            itemsMobile: false,
            autoPlay: false,
            navigation: true,
            navigationText: [ "<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>" ]
        } );
		// owlCarousel team
        var owl = jQuery( "#owl-team" );
        owl.owlCarousel( {
            margin:15,
        responsiveClass:true,
        responsive:{
        0:{
            items:1,
            nav:true,
            stagePadding: 0
        },
        320:{
            items:1,
            nav:true,
            stagePadding: 0
        },
        414:{
            items:1,
            nav:true,
            loop:true,
            stagePadding: 0
        },
        800:{
            items:2,
            nav:true,
            loop:true,
            stagePadding: 0
        },
        1024:{
            items:2,
            nav:true,
            loop:false,
            stagePadding: 160
         },
         1600:{
            items:3,
            nav:true,
            loop:false,
            stagePadding: 160
         },
        1920:{
            items:4,
            nav:true,
            loop:false,
            stagePadding: 160
         },
         2200:{
            items:5,
            nav:true,
            loop:false,
            stagePadding: 160
         },
         2300:{
            items:6,
            nav:true,
            loop:false,
            stagePadding: 160
         }
        },
        nav:true,
        navText : ['<i class="fa fa-angle-left" aria-hidden="true"></i>','<i class="fa fa-angle-right" aria-hidden="true"></i>']
        });

        // owlCarousel brand
        var owl = jQuery( "#owl-brand" );
        owl.owlCarousel( {
            items: 6,
            pagination: false,
            itemsDesktop: [ 1000, 4 ],
            itemsDesktopSmall: [ 900, 3 ],
            itemsTablet: [ 600, 2 ],
            itemsMobile: false,
            autoPlay: 2000,
            stopOnHover: true
        } );
  
  // owl projects detail
  var projectsBig = $("#projectsBig");
  projectsBig.owlCarousel( {
        margin:15,
        responsiveClass:true,
        responsive:{
        0:{
            items:1,
            nav:true,
            stagePadding: 0
        },
        320:{
            items:1,
            nav:true,
            stagePadding: 0
        },
        414:{
            items:1,
            nav:true,
            loop:true,
            stagePadding: 0
        },
        800:{
            items:1,
            nav:true,
            loop:true,
            stagePadding: 0
        },
        1024:{
            items:1,
            nav:true,
            loop:true,
            stagePadding: 0
         }
        },
        nav:true,
        navText : ['<i class="fa fa-angle-left" aria-hidden="true"></i>','<i class="fa fa-angle-right" aria-hidden="true"></i>']
        });
        
        // revolution slider
        var revapi;
        revapi = jQuery( '#revolution-slider' )
            .revolution( {
                delay: 15000,
                startwidth: 1170,
                startheight: 600,
                onHoverStop: "on",
                thumbWidth: 100,
                thumbHeight: 50,
                thumbAmount: 3,
                touchenabled: "on",
                stopAtSlide: -1,
                stopAfterLoops: -1,
                touchenabled: "on",
                navigationType: "none",
                dottedOverlay: "",
                fullWidth: "on",
                fullScreen: "on",
                shadow: 0
            } );
			
		// revolution slider
        var revapi;
        revapi = jQuery( '#revolution-slider-half' )
            .revolution( {
                delay:9000,
				startwidth:960,
				startheight:680,
                onHoverStop: "on",
                thumbWidth: 100,
                thumbHeight: 50,
                thumbAmount: 3,
                touchenabled: "on",
                stopAtSlide: -1,
                stopAfterLoops: -1,
                touchenabled: "on",
                navigationType: "none",
                dottedOverlay: "",
                fullWidth: "on",
                shadow: 0
            } );
			
			
 } );