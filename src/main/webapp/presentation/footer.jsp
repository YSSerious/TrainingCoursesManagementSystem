        </div>
    </div>
    <footer class="footer">
        <div class="container">
            <p class="text-center">Copyright (really? a u kidding?) © 2016 TCMS</p>
        </div>
    </footer>
        <script>
            $('a').each(function(){
                $(this).attr('onclick','window.location.href="'+$(this).attr('href')+'"');
                $(this).removeAttr('href');
            });
        </script>
</body>
</html>