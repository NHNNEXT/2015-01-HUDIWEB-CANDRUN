/**
 * 
 */


var Tabs = (function() {

  var toggler = $('.views-toggle');
  var tabs 		= $('li.tabs__item');
  var toggled = false;
  
  var transform = function(el, value) {
    el.css('transform', value);
    el.css('-webkit-transform', value);
    el.css('-ms-transform', value);
  };
   var transition = function(el, value) {
    el.css('transition', value);
    el.css('-webkit-transition', value);
    el.css('-ms-transition', value);
  };
  
  var moveContent = function() {
    if (!toggled) {
      toggled = true;
    } else {
      toggled = false;
    }
    
    moveTabs(toggled);
  
    return false;
  };
  
  var moveTabs = function(a) {
    var transY, scale;
    
    if (a) {
      tabs.css({
        'opacity': '1',
        'box-shadow': '0 30px 60px rgba(0,0,0,0.4)',
        'cursor': 'pointer'
      });
     
      tabs.each(function(index) {
        transY 	= index * 10;        
        scale		= 0.5 + index/25;
        
        transform($(this), 'translate3d(0,' + transY + 'vh, 0) scale(' + scale + ')');
      });
      
      toggler.addClass('views-toggle--hidden');
    } else {
      transform(tabs, 'translate3d(0,0,0) scale(1)');
    }
  };
  
  var switchTabs = function() {
    var selected = $(this);
    var others = selected.siblings('li');
    
    if (toggled) {
      transition(others, 'transform 0.3s cubic-bezier(0.755, 0.05, 0.855, 0.06)');
      transform(others, 'translate3d(0, 100%, 0) scale(1)');
      transform(selected, 'translate3d(0,0,0) scale(1)');
      tabs.css({
        'box-shadow': '0 30px 60px rgba(0,0,0,0.4)',
        'cursor': 'default'
      });
      toggled = false;

      selected.on('transitionend webkitTransitionend', function() {
        toggler.removeClass('views-toggle--hidden');
        others.css({
          'opacity': '0'
        });
        transform(others, 'translate3d(0, 100%, 0) scale(0)');
        transition(others, 'transform 0.9s cubic-bezier(0.23, 1, 0.32, 1)');
        selected.off('transitionend webkitTransitionend');
      });
    }
  };
  
  var setup = function() {
    toggled = true;
    moveTabs(toggled);
  };

  var init = function() {
    $(document).on('ready', setup);
		toggler.on('click touchstart', moveContent);
    tabs.on('click touchstart', switchTabs);
  };
  
  return {
    init: init
  };
  
}());

Tabs.init();