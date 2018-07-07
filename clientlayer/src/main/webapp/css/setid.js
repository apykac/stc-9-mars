$(".m_editSubject").click(function () {
    document.getElementById("m_id").value = $(this).attr("idsubj");
    document.getElementById("m_name").value = $(this).attr("namesubj");
});
$(".m_editLessons").click(function () {
    document.getElementById("l_id").value = $(this).attr("idless");
    document.getElementById("l_subjid").value = $(this).attr("subjidless");
    document.getElementById("l_date").value = $(this).attr("dateless");
    document.getElementById("l_name").value = $(this).attr("nameless");
});