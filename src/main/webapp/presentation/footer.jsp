        </div>
    </div>
    <footer class="footer">
        <div class="container">
            <div class="text-center">
                <div class="language" data-language="uk">
                    <a><spring:message code="language.uk"/></a>
                </div>
                <h5>&nbsp;|&nbsp;</h5>
                <div class="language" data-language="en">
                    <a><spring:message code="language.en"/></a>
                </div>
                <h5>&nbsp;&nbsp;&nbsp;</h5>
                <h5>Copyright (really? a u kidding?) © 2016 TCMS</h5>
            </div>
        </div>

    </footer>
        <script>
            $(document).ready(function(){
                $('.language').on('click', function(elem) {
                    document.cookie = "lang=" + this.dataset["language"] + ";path=/";
                    location.reload();
                })
            });
            $('a').each(function(){
                $(this).attr('link',$(this).attr('href'));
                $(this).removeAttr('href');
            });
            $('a').click(function (e) {
                if((e.button === 0 && e.ctrlKey)||e.button === 1){ // Click without ctrl
                    e.preventDefault();
                    // open local page
                    window.open($(this).attr('link'));
                }else{
                    window.location = $(this).attr('link');
                }
            });
            $(document).ready(function(){
                $('[data-toggle="tooltip"]').tooltip();   
            });   
        </script>
</body>
</html>