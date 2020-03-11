//  jpreLoader------------------

$('#main').jpreLoader({
	loaderVPos: '50%',
	autoClose: true,
	}, 
	function() {
		$('#main').animate({"opacity":'1'},{queue:false,duration:700,easing:"easeInOutQuad"});
		contanimshow();	
});	

//  definition of mobile browser------------------
	
	var isMobile = { 
       Android: function() {
            return navigator.userAgent.match(/Android/i);
        },
        BlackBerry: function() {
            return navigator.userAgent.match(/BlackBerry/i);
        },
        iOS: function() {
            return navigator.userAgent.match(/iPhone|iPad|iPod/i);
        },
        Opera: function() {
            return navigator.userAgent.match(/Opera Mini/i);
        },
        Windows: function() {
            return navigator.userAgent.match(/IEMobile/i);
        },
        any: function() {
            return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
        }
		
    };

function initVking() {

	"use strict"; 
	
// services-slider   ----------------------------------------

	$('#services-slider li.slide').hide();
	$('#services-slider li.slide:first').show();
	$('#services-slider li.slide:first').addClass('actser');
	$('.services-nav a.slider-link').on('click', function() {	
		$('.services-nav a.slider-link').removeClass('activeslide');
		$(this).addClass('activeslide');	
		$('#services-slider li.slide').removeClass('actser');
		var currentTab=$(this).attr('href');
		$('#services-slider li.slide').hide(500,function(){
			$('.services-container').animate({'opacity':'0' , top:'-5%'},1);		
		});
		$(currentTab).show(500,function(){			
			$('.services-container').animate({'opacity':'1' , top:0},1000);			
		});
		return false;
	});
		
// magnificPopup ----------------------------------------	

	$('.popup-youtube, .popup-vimeo').magnificPopup({
		disableOn: 700,
		type: 'iframe',
		removalDelay: 600,
		mainClass: 'my-mfp-slide-bottom', 
	});
	
	$('.popup-gallery').magnificPopup({
		delegate: 'a',
		type: 'image',
		tLoading: 'Loading image #%curr%...',
		removalDelay: 600,
		mainClass: 'my-mfp-slide-bottom', 
		gallery: {
			enabled: true,
			navigateByImgClick: true, 
			preload: [0,1] 
		},
		image: {
			tError: '<a href="%url%">The image #%curr%</a> could not be loaded.',
		}

	});	
	
	$('.image-popup').magnificPopup({
		type: 'image',
		closeOnContentClick: false,
		removalDelay: 600,
		mainClass: 'my-mfp-slide-bottom',
		image: {
			verticalFit: false
		}
	});	
	
// mixitup ----------------------------------------
	
	$('#folio_container').mixitup({
		targetSelector: '.box',
		effects: ['fade','scale'],
		easing: 'snap',
		transitionSpeed:800,
		layoutMode: 'grid',
    	targetDisplayGrid: 'inline-block',
    	targetDisplayList: 'block',
	});

	$("#options li").bind('click', function() {
		$("#options li").removeClass('actcat');
		$(this).addClass('actcat');
	}); 
		
// project-slider ----------------------------------------			
		
	var owl3 = $("#single-media");
		owl3.owlCarousel({
			navigation:false,
			slideSpeed : 500,
			pagination :false,
			autoHeight : false,
			singleItem:true,
		});	
		$(".single-media .next-slide").click(function(){
      		owl3.trigger('owl.next');		
    	});
		$(".single-media .prev-slide").click(function(){
      		owl3.trigger('owl.prev');		
    	});
			
// Twitter feed ----------------------------------------	
	
	if ($('#twitter-feed').length) {
		$('#twitter-feed').tweet({
			username: 'katokli3mmm',
			join_text: 'auto',
			avatar_size: 0,
			count: 4
		});
			
	$('#twitter-feed').find('ul').addClass('twitter-slider');
	$('#twitter-feed').find('ul li').addClass('item');
	var owl = $(".twitter-slider");
		owl.owlCarousel({
			navigation:false,
			slideSpeed : 500,
			pagination :false,
			autoHeight : true,
			singleItem:true,
		});	
		$(".twitter-feed-holder .next-slide").click(function(){
      		owl.trigger('owl.next');		
    	});
		$(".twitter-feed-holder .prev-slide").click(function(){
      		owl.trigger('owl.prev');		
    	});
	  };
	  	
// Subscribe   ----------------------------------------

	$('.subscriptionForm').submit(function(){		
		var email = $('#subscriptionForm').val();
		$.ajax({
			url:'php/subscription.php',
			type :'POST',
			dataType:'json',
			data: {'email': email},success: function(data){
				if(data.error){
					$('#error').fadeIn()
				}
				else{
					$('#success').fadeIn();
					$("#error").hide();}
				}
			});
		return false
	});
	
	$('#subscriptionForm').focus(function(){
		$('#error').fadeOut();
		$('#success').fadeOut();	
	});
	$('#subscriptionForm').keydown(function(){	
		$('#error').fadeOut();
		$('#success').fadeOut();		
	});	 
	
// contact form   ----------------------------------------

	$('#contactform').submit(function(){
		var action = $(this).attr('action');
		$("#message").slideUp(750,function() {
		$('#message').hide();
 		$('#submit').attr('disabled','disabled');
		$.post(action, {
			name: $('#name').val(),
			email: $('#email').val(),
			comments: $('#comments').val()
		},
			function(data){
				document.getElementById('message').innerHTML = data;
				$('#message').slideDown('slow');
				$('#submit').removeAttr('disabled');
				if(data.match('success') != null) $('#contactform').slideDown('slow');
			});

		});
		return false;
	});	
	$("#contactform input, #contactform textarea").keyup(function(){		
			$("#message").slideUp(1500)			
	});	
	
// Map(   ----------------------------------------	

	$('#map_addresses').gMap({
		latitude: 43.6007628,
		longitude: 1.432841,
		zoom: 12,
		maptype: 'ROADMAP',
		markers:[
			{
				latitude: 43.6007628,
				longitude: 1.432841,
				icon: {
					image: "images/entreprise/android.png",
					iconsize: [32, 32],
					iconanchor: [0, 0]
				}
			},
		]
	});

// Counter   ----------------------------------------
	
	function number(num, content, target, duration) {
		if (duration) {
			var count    = 0;
			var speed    = parseInt(duration / num);
			var interval = setInterval(function(){
				if(count - 1 < num) {
					target.html(count);
				}
				else {
					target.html(content);
					clearInterval(interval);
				}
				count++;
			}, speed);
		} 
		else {
			target.html(content);
		}
	}
    function stats(duration) {
		$('.stats .num').each(function() {
			var container = $(this);
			var num = container.attr('data-num');
			var content  = container.attr('data-content');
			number(num, content, container, duration);
        });
	}		
	    var $i = 1;

		if ($i === 1) { stats(1200); }
		$i++;
		
// Skillbar  ----------------------------------------	

	$('.animaper').appear(); 
		$(document.body).on('appear', '.skillbar-box', function() {	
			$(this).find('div.skillbar-bg').each(function(){	
			$(this).find('.custom-skillbar').animate({width:$(this).attr('data-percent')},1500);
		});
	});	

// Scroll  top  ----------------------------------------
	
	$('.scroll-btn').on('click', function() {
		var numpos = 0;
		if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') 
		|| location.hostname == this.hostname) {
			var target = $(this.hash);
			target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
			if (target.length) {
				$('html,body').animate({scrollTop: target.offset().top - numpos}, 1000, 'easeOutExpo');
			return false;
			}
		}	
	});	

// Hover box  ----------------------------------------

	$('.box').hover(function(){
		var cursl = $(this);
		var th = $(this).find('span.raster');
		var thi = $(this).find('div.work-info');
		var $tElems = $(this).find('a');
		var al = {queue:true,duration:500,easing:"easeOutCubic"};
		if ($(this).hasClass("notvisible") ) {				
			th.stop(true,true).animate({'opacity':'0.5'},al);
			thi.stop(true,true).animate({'bottom':'0'},al);
			cursl.removeClass('notvisible');
			$tElems.delay(1200).stop(true,true).animate({'opacity' : '1' , top :'10px'},{queue:true,duration:900,easing:"easeInOutElastic"}); 
		} 
		else {
			th.stop(true,true).animate({'opacity':'0'},al);
			thi.stop(true,true).animate({'bottom':'-100%'},al);
			setTimeout( function(){	
 				$tElems.stop(true,true).animate({'opacity' : '0' ,top :'-50%'},100); 
			},200);
			cursl.addClass('notvisible');
		}	
		return false;		
	});	

	$('.custom-image').hover(function(){
		var cursl2 = $(this);
		var th2 = $(this).find('span.raster');
		var $tElems2 = $(this).find('a');
		var al2 = {queue:true,duration:500,easing:"easeOutCubic"};
		if (cursl2.hasClass("notvisible2") ) {			
			th2.stop(true,true).animate({'opacity':'0.5'},al2);
			cursl2.removeClass('notvisible2');
			$tElems2.delay(1200).stop(true,true).animate({'opacity' : '1' , top :'50%'},{queue:true,duration:900,easing:"easeInOutElastic"}); 			
		} 
		else {
			th2.stop(true,true).animate({'opacity':'0'},al2);
			setTimeout( function(){	
 				$tElems2.stop(true,true).animate({'opacity' : '0' ,top :'-50%'},100); 
			},200);
			cursl2.addClass('notvisible2');
		}	
		return false;
	});	

}

 // Show info   ----------------------------------------
 
	$(".show-info").click( function(){
		setTimeout( function(){	
			$('.twitter-feed-holder').slideUp(200);
		},530);		
		if ($('.p-info').hasClass("not-vis") ) {
			$('.p-info').fadeIn(10);
			$('.p-info').removeClass('not-vis');
			setTimeout( function(){		
				$('.p-info').animate({'margin-top':'22px', opacity:1},{queue:true,duration:200,easing:"swing"});	
			},10);	
		} 
		else {
			$('.p-info').animate({'margin-top':'-22px', opacity:0},{queue:true,duration:200,easing:"swing"});
			setTimeout( function(){	
				$('.p-info').addClass('not-vis');	
				$('.p-info').fadeOut(10);
			},230);
		}	
		return false;
	});

// Show twitter   ----------------------------------------

	$('.show-twitter').click(function(){	
		$('.twitter-feed-holder').slideToggle(200);
		setTimeout( function(){	
			$('.p-info').animate({'margin-top':'-22px', opacity:0},{queue:true,duration:200,easing:"swing"});
			setTimeout( function(){	
				$('.p-info').addClass('not-vis');	
				$('.p-info').fadeOut(10);
			},230);
		},530);
	});
	
// Active nav  ----------------------------------------	
	
	$('.navigation li a.ajax').click(function(){
		hidemenu();
		$('.navigation li a.ajax').removeClass('active');
		$(this).addClass('active');
	});
	
// Share  ----------------------------------------	
	
	$('.shareSelector').socialShare({
		social: 'facebook,twitter,google,pinterest,linkedin',
		whenSelect: true,
		selectContainer: '.shareSelector',
		blur: true
	});		
	
  	trueMobile = isMobile.any();

	if (trueMobile == null){
		

	}
	
// Content animations  ----------------------------------------
		
	function contanimshow(){
		setTimeout(function(){
			$('.anim-content').animate({'top':'0', opacity:1},{queue:true,duration:500,easing:"swing"});
		},300);	
	}	

// Closing windows when you click on back button ----------------------------------------

	function hidemodals() {
	
		$('.p-info').animate({'margin-top':'-22px', opacity:0},{queue:true,duration:200,easing:"swing"});
		setTimeout( function(){	
			$('.p-info').addClass('not-vis');	
			$('.p-info').fadeOut(10);
		},230); 
		$('body').children().removeClass('blurred');
		$('.arthrefSocialShare').find('.overlay').removeClass('active');
		$('.arthrefSocialShare').find('ul').removeClass('active');
		setTimeout(function(){
			$('.arthrefSocialShare').find('.overlay').css('display','none');
			$('.arthrefSocialShare').remove()
		},300);	
		var magnificPopup = $.magnificPopup.instance; 
		magnificPopup.close(); 	
	}

// Closing menu on mobile ----------------------------------------

	function hidemenu(){
	
		var ww2 = $(window).width();
		setTimeout( function(){
				if( ww2 < 979){
			$('.nav-holder nav').slideUp(600);	
				
		}
		},1830);	
	}
	
// Mobile nav button ----------------------------------------	

	$(window).resize(function(){
		var ww2 = $(window).width();
		if( ww2 < 959){
			$('.nav-holder nav').css('display','none')		
		}
		else if (ww2 > 959){
			$('.nav-holder nav').css('display','block')			
		}	
	});
	$('.nav-button').bind('click' , function(){
		$('nav').slideToggle(600);
	});
