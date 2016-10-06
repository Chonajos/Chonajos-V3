var DOM = {
    main: function () {
        this.init();
    },
    init: function () {
        this.calendarLocaleEspaniol();
        this.menu();

    },
    calendarLocaleEspaniol: function () {
        PrimeFaces.locales['es'] = {
            closeText: 'Cerrar',
            prevText: 'Anterior',
            nextText: 'Siguiente',
            monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
            monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
            dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
            dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
            dayNamesMin: ['D', 'L', 'M', 'M', 'J', 'V', 'S'],
            weekHeader: 'Semana',
            firstDay: 0,
            isRTL: false,
            showMonthAfterYear: false,
            yearSuffix: '',
            timeOnlyTitle: 'Sólo hora',
            timeText: 'Tiempo',
            hourText: 'Hora',
            minuteText: 'Minuto',
            secondText: 'Segundo',
            currentText: 'Fecha actual',
            ampm: false,
            month: 'Mes',
            week: 'Semana',
            day: 'Día',
            allDayText: 'Todo el día'
            
        };
   },
    menu: function () {

        $('[data-toggle=collapse]').click(function () {

            // toggle icon
            if ($(this).attr('tercerNivel') != "nivelTres") {
                $('.collapse').each(function () {
                    if ($(this).hasClass('in')) {

                        $(this).collapse('toggle');
                    }
                });
            }

        });


        $("#btnMenuRes").on("click", function () {


            if ($('.sidebar').hasClass('responsivOff')) {
                $('#contMenuResp').show();
                $('.sidebar').show();
                $('div.sidebar').addClass('responsivOn');
                $('div.sidebar').removeClass('responsivOff');

            } else {
                $('div.sidebar').addClass('responsivOff');
                $('div.sidebar').removeClass('responsivOnn');
                $('#contMenuResp').hide();
            }

        });

        $(window).bind("load resize", function () {

            if ($(this).width() >= 753) {
                $('div.sidebar').addClass('responsivOff');
                $('div.sidebar').removeClass('responsivOnn');
                $('div.sidebar').hide();
            }
            if ($(this).width() < 753) {
                $('div.sidebar').addClass('responsivOff');
                $('div.sidebar').removeClass('responsivOnn');
                $('div.sidebar').hide();
            }
        });
    },
    validFilter: function (element, character) {
        if ($(element).val().indexOf(character) != -1) {
            $(element).val($(element).val().slice(0, -1));
        }
    }
}
$(document).ready(function () {
    DOM.main();
//    setClassH3();

});

function trimSpaces(component) {

    var componenteId = component;
    s = document.getElementById(componenteId).value;
    s = s.replace(/(^\s*)|(\s*$)/gi, "");
    s = s.replace(/[ ]{2,}/gi, " ");
    s = s.replace(/\n /, "\n");
    document.getElementById(componenteId).value = s;

}

function setClassH3(){
    $( "h3" ).addClass("col-lg-6 col-md-7 col-sm-8 col-xs-12");
}