(function() {
	CKEDITOR.plugins.add('page', {
	    init: function(editor) {
			editor.addCommand('page', CKEDITOR.plugins.pageCmd);
			editor.ui.addButton('Page', {
				label: editor.lang.pagebreak,
				command: 'page'
			});
	    }
	});
	CKEDITOR.plugins.pageCmd = {
		exec : function(editor) {
			var nextPage = CKEDITOR.dom.element.createFromHtml('<p>[NextPage][/NextPage]</p>', editor.document);
			var ranges = editor.getSelection().getRanges( true );
			for (var range,i=ranges.length-1; i >= 0; i--) {
				range = ranges[i];
				if (i < ranges.length -1 ) {
					nextPage = nextPage.clone(true);				
				}
				range.splitBlock( 'p' );
				range.insertNode( nextPage );
				if (i==ranges.length-1 ) {
					var next = nextPage.getNext();
					range.moveToPosition(nextPage, CKEDITOR.POSITION_AFTER_END);
					if (!next || next.type==CKEDITOR.NODE_ELEMENT && !next.isEditable()) {
						range.fixBlock(true, editor.config.enterMode==CKEDITOR.ENTER_DIV ? 'div' : 'p');					
					}
					range.select();
				}
			}
		}
	}
})();