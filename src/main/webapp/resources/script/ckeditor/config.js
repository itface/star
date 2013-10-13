/*
Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.fullPage = true;
	 config.enterMode = CKEDITOR.ENTER_BR;
	   config.shiftEnterMode = CKEDITOR.ENTER_P;
	config.UseBROnCarriageReturn = true ;
	config.ProcessHTMLEntities = false;
	config.entities = true;
	//config.protectedSource.push(/<pre[sS]*?pre>/g);
	//config.protectedSource.push( /<\?[\s\S]*?\?>/g );
	//config.docType = '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd>';
};
