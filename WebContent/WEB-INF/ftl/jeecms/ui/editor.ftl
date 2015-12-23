<#--
<textarea name="textarea"></textarea>
-->
<#macro editor
	name value="" height="230"
	fullPage="false" toolbarSet="My"
	label="" noHeight="false" required="false" colspan="" width="100" help="" helpPosition="2" colon=":" hasColon="true"
	maxlength="65535"
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange=""
	>
<#include "control.ftl"/><#rt/>
<textarea id="${name}" name="${name}">${value}</textarea>  
<script type="text/javascript">
$(function() {
   	CKEDITOR.replace( '${name}' ,
   			{
		   		filebrowserUploadUrl : '${base+appBase}/fck/upload.do?Type=File',  
		   		filebrowserImageUploadUrl : '${base+appBase}/fck/upload.do?Type=Image',  
		   		filebrowserFlashUploadUrl : '${base+appBase}/fck/upload.do?Type=Flash'  
   		     }
   	);  
 });
</script>
<#include "control-close.ftl"/><#rt/>
</#macro>