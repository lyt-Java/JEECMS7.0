/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	    config.language = 'zh-cn'; // 配置语言  
	    config.width = 'auto'; // 宽度  
	    config.height = '300px'; // 高度  
	    config.skin = 'kama';// 界面v2,kama,office2003  
	    config.toolbar = 'MyToolbar';// 工具栏风格（基础'Basic'、全能'Full'、自定义）
	    //自定义的工具栏      
	    config.toolbar_MyToolbar =  
	    [  
	        ['Source','-','Save','Preview','Print'],  
	        ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Scayt'],  
	        ['Undo','Redo','-','Find','Replace','-','SelectAll'],  
	        [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','TextColor','BGColor','-','RemoveFormat'],
	        ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
	        ['Styles','Format','Font','FontSize'],  
	        ['Link','Unlink','Anchor'],  
	        ['Maximize','autoformat','-'], 
		    ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar'],  
		    [ 'Page' ]
	    ];  
      	config.toolbar_Full = [
      		{ name: 'document', items : [ 'Source','-','Save','NewPage','DocProps','Preview','Print','-','Templates' ] },
      		{ name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
      		{ name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ] },
      		{ name: 'forms', items : [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 
      	        'HiddenField' ] },
      		'/',
      		{ name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },
      		{ name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv',
      		'-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] },
      		{ name: 'links', items : [ 'Link','Unlink','Anchor' ] },
      		{ name: 'insert', items : [ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak','Iframe' ] },
      		'/',
      		{ name: 'styles', items : [ 'Styles','Format','Font','FontSize' ] },
      		{ name: 'colors', items : [ 'TextColor','BGColor' ] },
      		{ name: 'tools', items : [ 'Maximize', 'ShowBlocks','-','About' ] }
      	];	
      	config.toolbar_Basic = [
      		['Bold', 'Italic', '-', 'NumberedList', 'BulletedList', '-', 'Link', 'Unlink','-','About']
      	];
	    config.extraPlugins = 'autoformat,page';
};

CKEDITOR.on( 'instanceReady', function( ev ) { with (ev.editor.dataProcessor.writer) {
	setRules("p", {indent : true, breakBeforeOpen : true, breakAfterOpen : false,breakBeforeClose : false,  breakAfterClose : true} );
	setRules("div", {indent : true, breakBeforeOpen : true, breakAfterOpen : false,breakBeforeClose : false,  breakAfterClose : true} );
	}
}); 

