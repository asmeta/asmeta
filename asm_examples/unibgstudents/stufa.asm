// LUGI SAVIO

asm stufa
import ../STDL/StandardLibrary
signature:
       enum domain Stato = {STANBY | VERIFICA_PELLET_PREACCENSIONE |
VERIFICA_SCARICO_FUMI | ACCESA | VERIFICA_PELLET_POSTACCENSIONE}
       domain Pellet subsetof Integer
       domain Potenza subsetof Integer
       domain Timer subsetof Integer
       domain Fuliggine subsetof Integer
       dynamic controlled stato_stufa: Stato
       dynamic controlled outMess: Any
       dynamic controlled pellet: Pellet
       dynamic controlled potenza: Potenza
       dynamic controlled timer: Timer
dynamic monitored tasto_potenza: String
dynamic monitored tasto_on_off: String
definitions:
domain Pellet = {0 : 50}
domain Potenza = {1 : 3}
domain Timer = {0 : 10}
domain Fuliggine = {0 : 100}
macro rule r_inizio_accensione =
                      if((stato_stufa=STANBY) and (tasto_on_off="on")) then
                                 stato_stufa := VERIFICA_PELLET_PREACCENSIONE
                      endif
macro rule r_inserire_pellet =
              par
                      stato_stufa := STANBY
              outMess := " inserire pellet"
                      pellet := 50
              endpar
macro rule r_avvio_ventola_fumi =
                      if(stato_stufa=VERIFICA_PELLET_PREACCENSIONE ) then
                               if(pellet<10) then
                                         r_inserire_pellet[]
                                   else
                                        stato_stufa := VERIFICA_SCARICO_FUMI
                               endif
macro rule r_pulire_tubi =
              par
                      stato_stufa := STANBY
              outMess := " pulire tubi"
                      fuliggine := 0
              endpar
macro rule r_accensione_fiamma =
                      if(stato_stufa=VERIFICA_PELLET_PREACCENSIONE ) then
                               if(fuliggine=100) then
                                         r_pulire_tubi[]
                                     else
                                          stato_stufa := ACCESA
                                 endif
macro rule r_diminuisci_timer_pellet=
                        timer := timer -1
                        if(tasto_potenza="aumenta")
                                          r_aumenta_potenza
                        endif
                        if(tasto_potenza="diminuisci")
                                          r_diminuisci_potenza
                        endif
                        if ((stato_stufa=ACCESA) and (timer=0) and (pellet<10)) then
                                 par
                    outMess := " inserire pellet"
                                  pellet := 50
                                  timer := 10
                                  fuliggine=fuliggine +1
                                 endpar
                                          else
                                           pellet := pellet - potenza
macro rule r_aumenta_potenza =
        if(potenza<3)
                potenza := potenza +1
        endif
macro rule r_diminuisci_potenza =
        if(potenza>1)
                potenza := potenza -1
        endif
macro rule r_spegni =
  if((stato_stufa=ACCESA) and (tasto_on_off="off")) then
                stato_stufa := STANBY
        endif
        main rule r_Main =
                 seq
                    r_inizio_accensione[]
                    r_avvio_ventola_fumi[]
                    r_accensione_fiamma[]
                    r_diminuisci_timer_pellet[]
                 endseq
// define the initial states
default init s0:
      function stato_stufa = STANBY
      function pellet = 50
      function timer = 10
      function potenza = 1
      function fuliggine = 0
