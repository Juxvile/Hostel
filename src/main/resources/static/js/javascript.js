// Маска номера телефона
$("#phone").mask("+375(99)999-99-99");
// Маска для ввода даты
$( function() {
    $("#datepicker").datepicker({minDate: 'today'});

} );

$(document).foundation();

const input = document.querySelector('input');

input.addEventListener('change', (event) => {
    const nowDate = Date.now();
    const inputDate = Date.parse(input.value)
    if (nowDate > inputDate ) {
        console.log('Уже в прошлом')
        return}
    console.log("будующее")
    return
});