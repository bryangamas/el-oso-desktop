(setf *puntos-humano* 0)
(setf *puntos-maquina* 0)
(setf *turno* nil)
(setf *tablero* nil)
(setf *cant-movimientos* 0)
(setf *pintar* nil) ; Aqui se guardaran las coordenadas de las casillas que se pintaran en la interfaz (los ultimos puntos realizados)

; Define el estado inicial del juego
(defun inicializar (n turno)
                            (setf *n* n)
                            (setf *estado-inicial* (make-array (list n n)))
                            (setf *tablero* *estado-inicial*)
			    (setf *turno* turno)
                            (setf *puntos-humano* 0)
                            (setf *puntos-maquina* 0)
                            (setf *cant-movimientos* 0))
				
; Pintar pinta 				
(defun pintar (x y)
                   (setf *pintar* (append *pintar* (list (list x y)))))

; Determina la cantidad de puntos ganados al colocar una S en el punto x (fila), y (columna)
(defun PGS (x y) (setq aux 0 *pintar* nil)
					(pintar x y)
					(if (and (> (- *n* 1) x) (< 0 x)  (> (- *n* 1) y) (< 0 y)) (
							 cond ((AND (equal (aref *tablero* (- x 1) (- y 1)) 'O ) (equal (aref *tablero* (+ x 1) (+ y 1)) 'O ))
								 (setq aux (+ aux 1))  
								 (pintar (- x 1) (- y 1))
								 (pintar (+ x 1) (+ y 1))
					)))

					(if (and (> (- *n* 1) x) (< 0 x)) (
							 cond ((AND (equal (aref *tablero* (- x 1) y ) 'O ) (equal (aref *tablero* (+ x 1) y) 'O ))
								 (setq aux (+ aux 1))
								 (pintar (- x 1) y)
								 (pintar (+ x 1) y)
					)))
						   
					(if (and (> (- *n* 1) x) (< 0 x)  (> (- *n* 1) y) (< 0 y)) (
							 cond ((AND (equal (aref *tablero* (- x 1) (+ y 1)) 'O ) (equal (aref *tablero* (+ x 1) (- y 1)) 'O ))
								 (setq aux (+ aux 1))
								 (pintar (- x 1) (+ y 1))
								 (pintar (+ x 1) (- y 1))
					)))

					(if (and (> (- *n* 1) y) (< 0 y)) (
							 cond ((AND (equal (aref *tablero* x (+ y 1)) 'O ) (equal (aref *tablero* x (- y 1)) 'O ))
								 (setq aux (+ aux 1))  
								 (pintar x (+ y 1))
								 (pintar x (- y 1))
					)))
					aux
                  )

; Regla que permite a la computadora escribr una S, si es su turno
(defun COM_ESCRIBE_S (x y) 
                   ( cond 
                          ((and (equal *turno* 'C)(> *n* x) (<= 0 x) (> *n* y) (<= 0 y) (null (aref *tablero* x y))) 
		          ( setf (aref *tablero* x y ) 'S)  
		          ( setf auxiliar (PGS x y))
	                  (if (= auxiliar 0) ( setf *turno* 'H) (setf *puntos-maquina* (+ auxiliar *puntos-maquina*)))
					  (setf *cant-movimientos* (+ 1 *cant-movimientos*))
	                  (verificar)
)))
 

; Regla que permite al humano escribr una S, si es su turno
(defun HUM_ESCRIBE_S (x y) 
                   ( cond 
                          ((and (equal *turno* 'H)(> *n* x) (<= 0 x) (> *n* y) (<= 0 y) (null (aref *tablero* x y))) 
		          ( setf (aref *tablero* x y ) 'S)  
		          ( setf auxiliar (PGS x y))
	                  (cond ((= auxiliar 0)  (setf *turno* 'C)) (t (setf *puntos-humano* (+ auxiliar *puntos-humano*))))
	                  (setf *cant-movimientos* (+ 1 *cant-movimientos*))
					  (verificar)
)))


; Determina la cantidad de puntos ganados al colocar una O en el punto x (fila), y (columna)
(defun PGO (x y) (setq aux 0 *pintar* nil)
					(pintar x y)
					(if (and (< 1 y) (< 1 x)) (
							 cond ((AND (equal (aref *tablero* (- x 2) (- y 2)) 'O ) (equal (aref *tablero* (- x 1) (- y 1)) 'S ))
								 (setq aux (+ aux 1))  
								 (pintar (- x 2) (- y 2))
								 (pintar (- x 1) (- y 1))
					)))
					(if (> x 1) (
							 cond ((AND (equal (aref *tablero* (- x 2) y ) 'O ) (equal (aref *tablero* (- x 1) y) 'S ))
								 (setq aux (+ aux 1))  
								 (pintar (- x 2) y)
								 (pintar (- x 1) y)
					)))
					(if (and (> (- *n* 2) y) (< 1 x)) (
							 cond ((AND (equal (aref *tablero* (- x 2) (+ y 2)) 'O ) (equal (aref *tablero* (- x 1)(+ y 1)) 'S ))
								 (setq aux (+ aux 1))  
								 (pintar (- x 2) (+ y 2))
								 (pintar (- x 1) (+ y 1))
					)))
					(if  (> (- *n* 2) y)  (
							 cond ((AND (equal (aref *tablero*  x (+ y 2)) 'O ) (equal (aref *tablero*  x (+ y 1)) 'S ))
								 (setq aux (+ aux 1))  
								 (pintar x (+ y 2))
								 (pintar x (+ y 1)) 
					)))

					(if (and (> (- *n* 2) y) (> (- *n* 2) x) ) (
							 cond ((AND (equal (aref *tablero* (+ x 2) (+ y 2)) 'O ) (equal (aref *tablero* (+ x 1)(+ y 1)) 'S ))
								 (setq aux (+ aux 1)) 
								 (pintar (+ x 2) (+ y 2))
								 (pintar (+ x 1) (+ y 1)) 
					)))
					(if  (> (- *n* 2) x)  (
							 if (AND (equal (aref *tablero*  ( + x 2) y) 'O ) (equal (aref *tablero*   (+ x 1) y) 'S ))
							 (setq aux (+ aux 1))  ) )
					(if (and (> y 1) (> (- *n* 2) x) ) (
							 cond ((AND (equal (aref *tablero* (+ x 2) (- y 2)) 'O ) (equal (aref *tablero* (+ x 1)(- y 1)) 'S ))
								 (setq aux (+ aux 1))  
								 (pintar (+ x 2) (- y 2))
								 (pintar (+ x 1) (- y 1)) 
					)))
					(if (> y 1) (
							 cond ((AND (equal (aref *tablero* x (- y 2)) 'O ) (equal (aref *tablero* x (- y 1)) 'S ))
								 (setq aux (+ aux 1))  
								 (pintar x (- y 2))
								 (pintar x (- y 1)) 
					)))
					aux  
			)


; Regla que permite a la computadora escribr una O, si es su turno
( defun COM_ESCRIBE_O (x y) 
                   ( cond 
                          ((and (equal *turno* 'C)(> *n* x) (<= 0 x) (> *n* y) (<= 0 y) (null (aref *tablero* x y))) 
		          ( setf (aref *tablero* x y ) 'O)  
		          ( setf auxiliar (PGO x y))
	                  (if (= auxiliar 0)  ( setf *turno* 'H) (setf *puntos-maquina* (+ auxiliar *puntos-maquina*)))
					  (setf *cant-movimientos* (+ 1 *cant-movimientos*))
	                  (verificar)
)))


; Regla que permite al humano escribr una O, si es su turno
( defun HUM_ESCRIBE_O (x y) 
                   ( cond 
                          ((and (equal *turno* 'H)(> *n* x) (<= 0 x) (> *n* y) (<= 0 y) (null (aref *tablero* x y))) 
		          ( setf (aref *tablero* x y ) 'O)  
		          ( setf auxiliar (PGO x y))
	                  (if (= auxiliar 0)  ( setf *turno* 'C) (setf *puntos-humano* (+ auxiliar *puntos-humano*)))
					  (setf *cant-movimientos* (+ 1 *cant-movimientos*))
	                  (verificar)
)))


; Determina la cantidad de "OSOS" que se forman al colocar una S, asi como cuantos se quedan a la mitad (faltaria una letra para completar "OSO")
; Asi la funcion de evaluacion para S queda definida por:
; 	completo + mitad; si completo > 0
; 			- mitad	; si completo = 0
(defun FUNCION_EVAL_S (x y) (setf completo 0 mitad 0 aux1 0 aux2 0)
                         (cond ((and (> (- *n* 1) x) (< 0 x)  (> (- *n* 1) y) (< 0 y))
                              (cond ((equal (aref *tablero* (- x 1) (- y 1)) 'O) (setf aux1 2))                    
                                    ((null (aref *tablero* (- x 1) (- y 1))) (setf aux1 1)))
                              (cond ((equal (aref *tablero* (+ x 1) (+ y 1)) 'O) (setf aux2 2))                    
                                    ((null (aref *tablero* (+ x 1) (+ y 1))) (setf aux2 1)))
                              (if (= (+ aux1 aux2) 4) (setf completo (+ 1 completo))
                                (if  (= (+ aux1 aux2) 3)(setf mitad (+ 1 mitad))))
                              (setf aux1 0 aux2 0)))
                         
                         (cond ((> (- *n* 1) x 0)
                              (cond ((equal (aref *tablero* (- x 1) y ) 'O ) (setf aux1 2))                    
                                    ((null (aref *tablero* (- x 1) y )) (setf aux1 1)))
                              (cond ((equal (aref *tablero* (+ x 1) y) 'O ) (setf aux2 2))                    
                                    ((null (aref *tablero* (+ x 1) y )) (setf aux2 1)))
                              (if (= (+ aux1 aux2) 4) (setf completo (+ 1 completo))
                                (if  (= (+ aux1 aux2) 3)(setf mitad (+ 1 mitad))))
                              (setf aux1 0 aux2 0)))
                        
                         (cond ((and (> (- *n* 1) x 0) (> (- *n* 1) y 0))
                              (cond ((equal (aref *tablero* (- x 1) (+ y 1)) 'O) (setf aux1 2))                    
                                    ((null (aref *tablero* (- x 1) (+ y 1))) (setf aux1 1)))
                              (cond ((equal (aref *tablero* (+ x 1) (- y 1)) 'O) (setf aux2 2))                    
                                    ((null (aref *tablero* (+ x 1) (- y 1))) (setf aux2 1)))
                              (if (= (+ aux1 aux2) 4) (setf completo (+ 1 completo))
                                (if  (= (+ aux1 aux2) 3)(setf mitad (+ 1 mitad))))
                              (setf aux1 0 aux2 0)))
                        
                         (cond ((> (- *n* 1) y 0 )
                              (cond ((equal (aref *tablero* x (+ y 1)) 'O) (setf aux1 2))                    
                                    ((null (aref *tablero* x (+ y 1))) (setf aux1 1)))
                              (cond ((equal (aref *tablero* x (- y 1)) 'O) (setf aux2 2))                    
                                    ((null (aref *tablero* x (- y 1))) (setf aux2 1)))
                              (if (= (+ aux1 aux2) 4) (setf completo (+ 1 completo))
                                (if  (= (+ aux1 aux2) 3)(setf mitad (+ 1 mitad))))
                              (setf aux1 0 aux2 0)))  
                        
                        (if (> completo 0) (+ completo mitad) (- mitad)))




;; Determina la cantidad de "OSOS" que se forman al colocar una O, asi como cuantos se quedan a la mitad (faltaria una letra para completar "OSO")
; Asi la funcion de evaluacion para O queda definida por:
; 	completo + mitad; si completo > 0
; 			- mitad	; si completo = 0
(defun FUNCION_EVAL_O (x y) (setf completo 0 mitad 0 aux1 0 aux2 0) 
                         (cond ((and (< 1 y) (< 1 x))
                              (cond ((equal (aref *tablero* (- x 2) (- y 2)) 'O) (setf aux1 2))                    
                                    ((null (aref *tablero* (- x 2) (- y 2))) (setf aux1 1)))
                              (cond ((equal (aref *tablero* (- x 1) (- y 1)) 'S) (setf aux2 2))                    
                                    ((null (aref *tablero* (- x 1) (- y 1))) (setf aux2 1)))
                              (if (= (+ aux1 aux2) 4) (setf completo (+ 1 completo))
                                (if  (= (+ aux1 aux2) 3)(setf mitad (+ 1 mitad))))
                              (setf aux1 0 aux2 0)))
                         
                         (cond ((> x 1)
                              (cond ((equal (aref *tablero* (- x 2) y ) 'O ) (setf aux1 2))                    
                                    ((null (aref *tablero* (- x 2) y )) (setf aux1 1)))
                              (cond ((equal (aref *tablero* (- x 1) y) 'S ) (setf aux2 2))                    
                                    ((null (aref *tablero* (- x 1) y )) (setf aux2 1)))
                              (if (= (+ aux1 aux2) 4) (setf completo (+ 1 completo))
                                (if  (= (+ aux1 aux2) 3)(setf mitad (+ 1 mitad))))
                              (setf aux1 0 aux2 0)))
                        
                         (cond ((and (> (- *n* 2) y) (< 1 x))
                              (cond ((equal (aref *tablero* (- x 2) (+ y 2)) 'O) (setf aux1 2))                    
                                    ((null (aref *tablero* (- x 2) (+ y 2))) (setf aux1 1)))
                              (cond ((equal (aref *tablero* (- x 1) (+ y 1)) 'S) (setf aux2 2))                    
                                    ((null (aref *tablero* (- x 1) (+ y 1))) (setf aux2 1)))
                              (if (= (+ aux1 aux2) 4) (setf completo (+ 1 completo))
                                (if  (= (+ aux1 aux2) 3)(setf mitad (+ 1 mitad))))
                              (setf aux1 0 aux2 0)))
                        
                         (cond ((> (- *n* 2) y)
                              (cond ((equal (aref *tablero* x (+ y 2)) 'O) (setf aux1 2))                    
                                    ((null (aref *tablero* x (+ y 2))) (setf aux1 1)))
                              (cond ((equal (aref *tablero* x (+ y 1)) 'S) (setf aux2 2))                    
                                    ((null (aref *tablero* x (+ y 1))) (setf aux2 1)))
                              (if (= (+ aux1 aux2) 4) (setf completo (+ 1 completo))
                                (if  (= (+ aux1 aux2) 3)(setf mitad (+ 1 mitad))))
                              (setf aux1 0 aux2 0)))
			
                         (cond ((and (> (- *n* 2) y) (> (- *n* 2) x) )
                              (cond ((equal (aref *tablero* (+ x 2) (+ y 2)) 'O) (setf aux1 2))                    
                                    ((null (aref *tablero* (+ x 2) (+ y 2))) (setf aux1 1)))
                              (cond ((equal (aref *tablero* (+ x 1) (+ y 1)) 'S) (setf aux2 2))                    
                                    ((null (aref *tablero* (+ x 1) (+ y 1))) (setf aux2 1)))
                              (if (= (+ aux1 aux2) 4) (setf completo (+ 1 completo))
                                (if  (= (+ aux1 aux2) 3)(setf mitad (+ 1 mitad))))
                              (setf aux1 0 aux2 0)))
                         
                         (cond ((> (- *n* 2) x)
                              (cond ((equal (aref *tablero* (+ x 2) y ) 'O ) (setf aux1 2))                    
                                    ((null (aref *tablero* (+ x 2) y )) (setf aux1 1)))
                              (cond ((equal (aref *tablero* (+ x 1) y) 'S ) (setf aux2 2))                    
                                    ((null (aref *tablero* (+ x 1) y )) (setf aux2 1)))
                              (if (= (+ aux1 aux2) 4) (setf completo (+ 1 completo))
                                (if  (= (+ aux1 aux2) 3)(setf mitad (+ 1 mitad))))
                              (setf aux1 0 aux2 0)))
                        
                         (cond ((and (> y 1) (> (- *n* 2) x))
                              (cond ((equal (aref *tablero* (+ x 2) (- y 2)) 'O) (setf aux1 2))                    
                                    ((null (aref *tablero* (+ x 2) (- y 2))) (setf aux1 1)))
                              (cond ((equal (aref *tablero* (+ x 1) (- y 1)) 'S) (setf aux2 2))                    
                                    ((null (aref *tablero* (+ x 1) (- y 1))) (setf aux2 1)))
                              (if (= (+ aux1 aux2) 4) (setf completo (+ 1 completo))
                                (if  (= (+ aux1 aux2) 3)(setf mitad (+ 1 mitad))))
                              (setf aux1 0 aux2 0)))
                        
                         (cond ((and (> y 1) )
                              (cond ((equal (aref *tablero* x (- y 2)) 'O) (setf aux1 2))                    
                                    ((null (aref *tablero* x (- y 2))) (setf aux1 1)))
                              (cond ((equal (aref *tablero* x (- y 1)) 'S) (setf aux2 2))                    
                                    ((null (aref *tablero* x (- y 1))) (setf aux2 1)))
                              (if (= (+ aux1 aux2) 4) (setf completo (+ 1 completo))
                                (if  (= (+ aux1 aux2) 3)(setf mitad (+ 1 mitad))))
                              (setf aux1 0 aux2 0)))
                         (if (> completo 0) (+ completo mitad) (- mitad)))



; Agrega movimiento a lista aleatoria						 
(defun agregar (letra x y)
                   (setf list_ale (append list_ale (list (list letra x y)))))

				   
; Funcion que selecciona al azar un movimiento entre los que presentan FE = 0
(defun mov_ale ()
                   (cond ((not (null list_ale))
                         (setf aux (nth (random (length list_ale)) list_ale))
                         (setf letra (car aux) x (cadr aux) y (caddr aux)))))

						 
						 
; PRIMERO EL MEJOR:
; Elige a la mejor opcion entre todas las casillas vacias, asi como la letra a colocar en ella
; La funcion de evaluacion general queda definida por:
; 	FE(X, Y) = MAX ( funcion_eval_s(X, Y), funcion_eval_o(X, Y) )

; En el caso en el que la mejor opcion presente FE = 0, se eligira al azar entre todas las opciones que presenten la misma situacion (FE=0)
(defun pem()
                   (setf x 0 y 0 max -9 letra nil list_ale nil)
                   (dotimes (fila *n*)
                     (dotimes (columna *n*)
                       (cond ((null (aref *tablero* fila columna))
                            (setf aux (FUNCION_EVAL_S fila columna))
                            (if (= 0 aux) (agregar 'S fila columna))	; FE=0, se agrega a lista aleatoria
                            (if (> aux max)
                                (setf max aux x fila y columna letra 'S))
							(setf aux (FUNCION_EVAL_O fila columna))
							(if (= 0 aux) (agregar 'O fila columna))	; FE=0, se agrega a lista aleatoria
							(if (> aux max) (setf max aux x fila y columna letra 'O))))))
                   (if (= 0 max) (mov_ale))		; MAX (FE) = 0, entonces, se hace un movimiento aleatorio
                   (if (equal letra 'S) (com_escribe_s x y)(com_escribe_o x y))
				   (list letra x y)
				   )



(defun s (x y) (hum_escribe_s x y))

(defun o (x y) (hum_escribe_o x y))


(defun verificar ()
                   (cond ((= *cant-movimientos* (* *n* *n*))
                          (setf *turno* nil)
                          (cond ((> *puntos-humano* *puntos-maquina*) 1)
                                ((< *puntos-humano* *puntos-maquina*) 2)
                                (t 0)))))