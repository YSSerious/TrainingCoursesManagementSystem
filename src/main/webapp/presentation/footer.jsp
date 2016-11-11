        </div>
    </div>
    <footer class="footer">
        <div class="container">
            <div class="text-center">
                <div class="language" data-language="uk">
                    <a><spring:message code="language.uk"/></a>
                </div>
                <div class="language" data-language="en">
                    <a><spring:message code="language.en"/></a>
                </div>
            </div>
            <p class="text-center">Copyright (really? a u kidding?) © 2016 TCMS</p>
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
                $(this).attr('onclick','window.location.href="'+$(this).attr('href')+'"');
                $(this).removeAttr('href');
            });
        </script>
</body>
</html>