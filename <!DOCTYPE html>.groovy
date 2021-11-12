<html>
<head>
<meta charset=utf-8 />
<title>Slideshow gallery in a marker tooltip</title>
<meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />
<script src='https://api.mapbox.com/mapbox.js/v3.3.1/mapbox.js'></script>
<link href='https://api.mapbox.com/mapbox.js/v3.3.1/mapbox.css' rel='stylesheet' />
<style>
  body { margin:0; padding:0; }
  #map { position:absolute; top:0; bottom:0; width:100%; }
</style>
</head>
<body>
<style>
.popup {
  text-align:center;
  }
.popup .slideshow .image        { display:none; }
.popup .slideshow .image.active { display:block; }
.popup .slideshow img {
  width:100%;
  }
.popup .slideshow .caption {
  background:#fff;
  padding:10px;
  }
.popup .cycle {
  padding:10px 0 20px;
  }
  .popup .cycle a.prev { float:left; }
  .popup .cycle a.next { float:right; }
</style>

<div id='map'></div>

<!-- jQuery is required for this example. -->
<script src='https://code.jquery.com/jquery-1.11.0.min.js'></script>
<script>
L.mapbox.accessToken = 'pk.eyJ1IjoibWFyY29zdXJpYW5vIiwiYSI6ImNrdnZrdDRtbTA1Z3MzMG9lOHp5bnA5dXAifQ.HFiouDjzXoj0ZB4uecimAA';
var map = L.mapbox.map('map')
  .addLayer(L.mapbox.styleLayer('mapbox://styles/marcosuriano/ckvvkvl5k151715pfq0cw5afr'));

var myLayer = L.mapbox.featureLayer().addTo(map);

var geoJson = {
  type: 'FeatureCollection',
  features: [{
      type: 'Feature',
      "geometry": { "type": "Point", "coordinates": [9.2
,45.465]},
      "properties": {
          'title': 'Milano',
          'marker-color': '#ffff',
          'marker-symbol': 'monument',
          'marker-size': 'large',

          // Store the image url and caption in an array.
          'images': [
            ['https://static.open.online/wp-content/uploads/2020/03/MILANO-TAG.jpg','The U.S. Capitol after the burning of Washington during the War of 1812'],
            ['https://i.imgur.com/xND1MND.jpg','Ford\'s Theatre in the 19th century, site of the 1865 assassination of President Lincoln'],
            ['https://i.imgur.com/EKJmqui.jpg','The National Cherry Blossom Festival is celebrated around the city each spring.']
          ]
      }
  }, {
      type: 'Feature',
      "geometry": { "type": "Point", "coordinates": [9.3, 40.71]},
      "properties": {
          'title': 'New York City',
          'marker-color': '#ffff',
          'marker-symbol': 'city',
          'marker-size': 'large',
          'images': [
            ['https://i.imgur.com/exemdwr.png','Peter Minuit is credited with the purchase of the island of Manhattan in 1626.'],
            ['https://i.imgur.com/LHNDBpf.jpg','During the mid-19th Century, Broadway was extended the length of Manhattan.'],
            ['https://i.imgur.com/Pk3DYH1.jpg','Times Square has the highest annual attendance rate of any tourist attraction in the world.']
          ]

      }
  }]
};

// Add custom popup html to each marker.
myLayer.on('layeradd', function(e) {
    var marker = e.layer;
    var feature = marker.feature;
    var images = feature.properties.images
    var slideshowContent = '';

    for(var i = 0; i < images.length; i++) {
        var img = images[i];

        slideshowContent += '<div class="image' + (i === 0 ? ' active' : '') + '">' +
                              '<img src="' + img[0] + '" />' +
                              '<div class="caption">' + img[1] + '</div>' +
                            '</div>';
    }

    // Create custom popup content
    var popupContent =  '<div id="' + feature.properties.id + '" class="popup">' +
                            '<h2>' + feature.properties.title + '</h2>' +
                            '<div class="slideshow">' +
                                slideshowContent +
                            '</div>' +
                            '<div class="cycle">' +
                                '<a href="#" class="prev">&laquo; Previous</a>' +
                                '<a href="#" class="next">Next &raquo;</a>' +
                            '</div>'
                        '</div>';

    // http://leafletjs.com/reference.html#popup
    marker.bindPopup(popupContent,{
        closeButton: false,
        minWidth: 320
    });
});

// Add features to the map
myLayer.setGeoJSON(geoJson);

$('#map').on('click', '.popup .cycle a', function() {
    var $slideshow = $('.slideshow'),
        $newSlide;

    if ($(this).hasClass('prev')) {
        $newSlide = $slideshow.find('.active').prev();
        if ($newSlide.index() < 0) {
            $newSlide = $('.image').last();
        }
    } else {
        $newSlide = $slideshow.find('.active').next();
        if ($newSlide.index() < 0) {
            $newSlide = $('.image').first();
        }
    }

    $slideshow.find('.active').removeClass('active').hide();
    $newSlide.addClass('active').show();
    return false;
});

map.setView([42.184,12.300], 6,32);
</script>
</body>
</html>