jQuery().ready(function(){
		   
/*	    $('div.home').jCarouselLite({//slider for homepage blog posts
    	 btnNext: '#home-blogposts-nav .next',
	     btnPrev: '#home-blogposts-nav .prev',
		 easing:'easeInOutExpo',
		 vertical: true,
		 speed:1000,
		 visible:1
	    });*/
	    
 
	    $('#featured-info').jCarouselLite({ //slider for homepage featured section info text
    	btnPrev:   '#home-featured-nav .prev', 
	    btnNext:   '#home-featured-nav .next', 
		easing:'easeInOutExpo',
		speed:2000,
		vertical:true,
		visible:1,
		auto:1
		});
		
		$('.testimonial-list').jCarouselLite({ //slider for testimonials
    	btnPrev:   '.testimonials-nav .prev', 
	    btnNext:   '.testimonials-nav .next', 
		easing:'easeInOutExpo',
		speed:1000,
		vertical:true,
		visible:1
		});
		
		$('.tweets').jCarouselLite({ //slider for twitter widget
    	btnPrev:   '.twitter-nav .prev', 
	    btnNext:   '.twitter-nav .next', 
		easing:'easeInOutExpo',
		speed:4000,
		vertical:true,
		visible:1,
		auto:1
		});
		
	    $('#images').jCarouselLite({ //slider for homepage featured images
    	btnPrev:   '#home-featured-nav .prev', 
	    btnNext:   '#home-featured-nav .next', 
		easing:'easeInOutExpo',
		speed:2000,
		visible:1,
		auto:1
		});
		
	
		var expanded = false;
		
	 	$("#bt-expander").click(function() { // portfolio item quick nav
	 	if (expanded == false) {
	 	$(this).addClass("expanded");
     	$("#work-list ul").slideDown({ speed: 500, easing: "easeOutExpo"});
		 	expanded = true;
     	} else {
     	expanded = false;
	 	$(this).removeClass("expanded");
     	$("#work-list ul").slideUp({ speed: 500, easing: "easeOutExpo"});
     	}
	   	});
	   	
 		$('#work-list ul li a').fadeTo("fast",0.70);
		//start the engine
		$('#work-list ul li a').hover(function() {
		$(this).fadeTo("fast",1);
		},
		function () {
        $(this).fadeTo("fast",0.70);
      	});


		
	});	//end of page functions
