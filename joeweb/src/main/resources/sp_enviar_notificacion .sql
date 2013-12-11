create or replace
PROCEDURE SP_ENVIAR_NOTIFICACION (
mailnotificacion1 in varchar2,
mailnotificacion2 in varchar2,
declienteempresa in varchar2,
porcentajetope in number,
cantidadtope in number,
sttipotope in varchar2,
detope in varchar2
)
as

mesg clob;
putmessage clob;
crlf varchar2(2):= chr(13) || Chr(10);
mailhost varchar2(30) := ltrim(rtrim('mail.pecsa.com.pe'));
mail_conn utl_smtp.connection;

envia varchar2(20) := ltrim(rtrim('NCPgn@pgn.com.pe'));
recibe varchar2(50) := ltrim(rtrim(mailnotificacion1));
recibe2 varchar2(50) := ltrim(rtrim(mailnotificacion2));
mensaje varchar2(1000):= ltrim(rtrim('Estimado(a) usuario(a)'));
asunto varchar2(200) := ltrim(rtrim('Notificacion de consumo de linea de credito - Sistema Club PGN'));

begin

  putmessage := '';
  putmessage := putmessage||'El cliente '||declienteempresa||' ha alcanzado el valor S/.'||
  cantidadtope||' (alerta '||detope||').';

  if(sttipotope = 'R')then
  putmessage := putmessage||' Se esta procediendo a bloquear las tarjetas.';
  end if;

  mail_conn := utl_smtp.open_connection(mailhost, 25);	        
  mesg:= 'Date: ' || to_char( sysdate, 'dd Mon yy hh24:mi:ss' ) || crlf;
  mesg:= mesg||'From: <'||envia||'>' || crlf;
  mesg:= mesg||'Subject: '|| asunto || crlf;          
  mesg:= mesg||'To: '||recibe|| crlf;
  mesg:= mesg||''|| crlf || mensaje || ': ';
  mesg:= mesg|| chr(10) || chr(13) || chr(10) || chr(13);
  mesg:= mesg||putmessage|| crlf;
  
  utl_smtp.helo(mail_conn, mailhost);
  utl_smtp.mail(mail_conn, envia);
  utl_smtp.rcpt(mail_conn, recibe);
  utl_smtp.data(mail_conn, mesg );
  
  utl_smtp.mail(mail_conn, envia);          
  utl_smtp.rcpt(mail_conn, recibe2);
  utl_smtp.data(mail_conn, mesg );
  utl_smtp.quit(mail_conn);

end sp_enviar_notificacion;