$("span[name='toggle-control']").click(function() {
  $('#target').toggle();
  $("span[name='toggle-control']").toggle();
});

$(document).ready(function(){
  $("#btn").click(function(){
    $("#panel").slideToggle('fast');   
  });
});