# GruenbeckCloud Binding

This Binding integrates [Grünbeck Softener - softliX:SD](https://www.gruenbeck.de/) devloped by Grünbeck.

The binding controls the softener devices from the gruenbeck cloud via HTTP Rest.

## Supported Things

The only softener series supported by this binding is: **softliQ:SD**

## Discovery

In general a softener needs to be registered to the gruenbeck cloud to be discovered by the binding.

The binding queries the gruenbeck cloud to find registered softener and offers them in the discovery to be added.

There is no periodical discovery, you have to run the manual discovery to find all your devices.

## Bridge Configuration

| Parameter     | Description                                                                                                                                                                                                                                           | Mandatory | Default |
|---------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|---------|
| email         | This is the Account Email required to log in to Grünbeck Cloud                                                                                                                                                                                        | yes       | none    |
| password      | This is the Password required to log in to Grünbeck Cloud                                                                                                                                                                                             | yes       | none    |
| refreshPeriod | This is the Frequency of the Polling Requests to the Grünbeck Cloud API. There are Limitsto the Number of Requests that can be sent to the API. The more often you poll, the better Status Updates, at the Risk of running out of your Request Quota. | yes       | 10s     |

## Softener Configuration

| Parameter    | Description                                                   | Mandatory | Default |
|--------------|---------------------------------------------------------------|-----------|---------|
| serialNumber | Serial Number of the Softener                                 | yes       | none    |
| id           | ID of the Softener (often includes the Serial Number as well) | yes       | none    |
| series       | Series of the Softener                                        | yes       | none    |
| name         | Device Name of the Softener                                   | yes       | none    |
| saltCapacity | The Salt Capacity of the Softener in Gram                     | yes       | none    |

## Channels

| channel                            | type     | description                                             | ready-only |
|------------------------------------|----------|---------------------------------------------------------|------------|
| pallowemail                        | Switch   | Email Notification                                      | true       |
| pallowpushnotification             | Switch   | Push Notification                                       | true       |
| pdlstauto                          | Switch   | Switch-over DST to ST                                   | true       |
| pntpsync                           | Switch   | Ntp Sync                                                | true       |
| pcfcontact                         | Switch   | Function Fault Signal Contact                           | true       |
| pknx                               | Switch   | KNX                                                     | true       |
| pmonflow                           | Switch   | Monitoring of Nominal Flow                              | true       |
| pmondisinf                         | Switch   | Disinfection Monitoring                                 | true       |
| pledatsaltpre                      | Switch   | Pre-alarm Salt Supply LED                               | true       |
| pbuzzer                            | Switch   | Audio Signal                                            | true       |
| prescaplimit                       | Number   | Residual Capacity Limit Value                           | true       |
| pcurrent                           | Number   | Setpoint Current                                        | true       |
| pload                              | Number   | Charge                                                  | true       |
| pforcedregdist                     | Number   | Interval of Forced Regeneration                         | true       |
| pmaintint                          | Number   | Maintenance Interval                                    | true       |
| pfreqregvalve                      | Number   | End Frequency Regeneration Valve                        | true       |
| pfreqblendvalve                    | Number   | [Hz] End frequency blending valve                       | true       |
| pledbright                         | Dimmer   | [%] Brightness                                          | true       |
| pvolume                            | Number   | [m³] Absorber treatment volume                          | true       |
| prawhard                           | Number   | [°dH] Raw water hardness                                | true       |
| psetsoft                           | Number   | [°dH] Setpoint of soft water hardness                   | true       |
| ppratesoftwater                    | Number   | [l/Imp] Soft water meter pulse rate                     | true       |
| pprateblending                     | Number   | [l/Imp] Blending water meter pulse rate                 | true       |
| pprateregwater                     | Number   | [l/Imp] Regeneration water meter pulse rate             | true       |
| psetcapmo                          | Number   | [m³x°dH] Capacity figure Monday                         | true       |
| psetcaptu                          | Number   | [m³x°dH] Capacity figure Tuesday                        | true       |
| psetcapwe                          | Number   | [m³x°dH] Capacity figure Wednesday                      | true       |
| psetcapth                          | Number   | [m³x°dH] Capacity figure Thursday                       | true       |
| psetcapfr                          | Number   | [m³x°dH] Capacity figure Friday                         | true       |
| psetcapsa                          | Number   | [m³x°dH] Capacity figure Saturday                       | true       |
| psetcapsu                          | Number   | [m³x°dH] Capacity figure Sunday                         | true       |
| pnomflow                           | Number   | [m³/h] Nominal flow rate                                | true       |
| ppressurereg                       | Number   | ppressurereg                                            | true       |
| pmonregmeter                       | Number   | [Min] Regeneration monitoring time                      | true       |
| pmonsalting                        | Number   | [Min] Salting monitoring time                           | true       |
| prinsing                           | Number   | [Min] Slow rinse                                        | true       |
| pbackwash                          | Number   | [l] Backwash                                            | true       |
| pwashingout                        | Number   | [l] Washing out                                         | true       |
| pminvolmincap                      | Number   | [l] Min. filling volume smallest cap                    | true       |
| pmaxvolmincap                      | Number   | [l] Max. filling volume smallest cap                    | true       |
| pminvolmaxcap                      | Number   | [l] Min. filling volume largest cap                     | true       |
| pmaxvolmaxcap                      | Number   | [l] Max. filling volume largest cap                     | true       |
| pmaxdurdisinfect                   | Number   | [Min] Longest switch-on time Cl cell                    | true       |
| pmaxresdurreg                      | Number   | [Min] Max Remaining time regeneration                   | true       |
| pbuzzfrom                          | Text     | [hh:mm] Audio signal release from                       | true       |
| pbuzzto                            | Text     | [hh:mm] Audio signal release until                      | true       |
| pmailadress                        | Text     | Maintenance company email                               | true       |
| pname                              | Text     | Maintenance company                                     | true       |
| ptelnr                             | Text     | Maintenance company phone number                        | true       |
| pmode                              | Number   | Operation mode                                          | false      |
| pmodemo                            | Number   | Indiv. Operating mode Monday                            | false      |
| pmodetu                            | Number   | Indiv. Operating mode Tuesday                           | false      |
| pmodewe                            | Number   | Indiv. Operating mode Wednesday                         | false      |
| pmodeth                            | Number   | Indiv. Operating mode Thursday                          | false      |
| pmodefr                            | Number   | Indiv. Operating mode Friday                            | false      |
| pmodesa                            | Number   | Indiv. Operating mode Saturday                          | false      |
| pmodesu                            | Number   | Indiv. Operating mode Sunday                            | false      |
| planguage                          | Number   | Current language                                        | true       |
| phunit                             | Number   | Hardness unit                                           | true       |
| pregmode                           | Number   | Time of regeneration                                    | false      |
| pprogout                           | Number   | Programmable output function                            | true       |
| pprogin                            | Number   | Programmable input function                             | true       |
| ppowerfail                         | Number   | Reaction to power failure > 5 min                       | true       |
| pmodedesinf                        | Number   | Activate/deactivate chlorine cell                       | true       |
| pled                               | Number   | Illuminated LED ring                                    | true       |
| pregmo1                            | Text     | [hh:mm] Define time of regeneration on Monday 1         | false      |
| pregmo2                            | Text     | [hh:mm] Define time of regeneration on Monday 2         | false      |
| pregmo3                            | Text     | [hh:mm] Define time of regeneration on Monday 3         | false      |
| pregtu1                            | Text     | [hh:mm] Define time of regeneration on Tuesday 1        | false      |
| pregtu2                            | Text     | [hh:mm] Define time of regeneration on Tuesday 2        | false      |
| pregtu3                            | Text     | [hh:mm] Define time of regeneration on Tuesday 3        | false      |
| pregwe1                            | Text     | [hh:mm] Define time of regeneration on Wednesday 1      | false      |
| pregwe2                            | Text     | [hh:mm] Define time of regeneration on Wednesday 2      | false      |
| pregwe3                            | Text     | [hh:mm] Define time of regeneration on Wednesday 3      | false      |
| pregth1                            | Text     | [hh:mm] Define time of regeneration on Thursday 1       | false      |
| pregth2                            | Text     | [hh:mm] Define time of regeneration on Thursday 2       | false      |
| pregth3                            | Text     | [hh:mm] Define time of regeneration on Thursday 3       | false      |
| pregfr1                            | Text     | [hh:mm] Define time of regeneration on Friday 1         | false      |
| pregfr2                            | Text     | [hh:mm] Define time of regeneration on Friday 2         | false      |
| pregfr3                            | Text     | [hh:mm] Define time of regeneration on Friday 3         | false      |
| pregsa1                            | Text     | [hh:mm] Define time of regeneration on Saturday 1       | false      |
| pregsa2                            | Text     | [hh:mm] Define time of regeneration on Saturday 2       | false      |
| pregsa3                            | Text     | [hh:mm] Define time of regeneration on Saturday 3       | false      |
| pregsu1                            | Text     | [hh:mm] Define time of regeneration on Sunday 1         | false      |
| pregsu2                            | Text     | [hh:mm] Define time of regeneration on Sunday 2         | false      |
| pregsu3                            | Text     | [hh:mm] Define time of regeneration on Sunday 3         | false      |
| pmonblend                          | Number   | Blending monitoring                                     | true       |
| poverload                          | Number   | System overloaded                                       | true       |
| pfreqregvalve2                     | Number   | [Hz] End frequency regeneration valve 2                 | true       |
| hardwareVersion                    | Text     | Hardware version                                        | true       |
| lastService                        | DateTime | [yyyy.mm.dd] Last maintenance                           | true       |
| mode                               | Number   | Operation mode                                          | true       |
| nextRegeneration                   | DateTime | Next regeneration                                       | true       |
| nominalFlow                        | Number   | Nominal flow                                            | true       |
| rawWater                           | Number   | [°dH] Raw water hardness                                | true       |
| softWater                          | Number   | [°dH] Soft water hardness                               | true       |
| softwareVersion                    | Text     | Software version                                        | true       |
| timeZone                           | Text     | Timezone                                                | true       |
| unit                               | Number   | Hardness unit                                           | true       |
| startup                            | DateTime | Startup date                                            | true       |
| type                               | Number   | Softener model type                                     | true       |
| hasError                           | Switch   | Error                                                   | true       |
| register                           | Switch   | Registered                                              | true       |
| last_error_message                 | Text     | Last error message                                      | true       |
| last_error_resolved                | Switch   | Last error resolved                                     | true       |
| last_error_date                    | DateTime | Last error date                                         | true       |
| last_error_type                    | Text     | Last error type                                         | true       |
| usage_value                        | Number   | Usage value                                             | true       |
| usage_date                         | DateTime | Usage date                                              | true       |
| ibuiltindev                        | Switch   | ibuiltindev                                             | true       |
| isncu                              | Text     | isncu                                                   | true       |
| mregpercent1                       | Number   | [%] till regeneration 1                                 | true       |
| mregpercent2                       | Number   | [%] till regeneration 2                                 | true       |
| mremregstep                        | Number   | Remaining amount / time of current regeneration step    | true       |
| mregstatus                         | Number   | Regeneration step                                       | true       |
| mresidcap1                         | Number   | [%] Residual capacity 1                                 | true       |
| mresidcap2                         | Number   | [%] Residual capacity 2                                 | true       |
| mrescapa1                          | Number   | [m³] Soft water Exchanger 1                             | true       |
| mrescapa2                          | Number   | [m³] Soft water Exchanger 2                             | true       |
| mmaint                             | Number   | [d] Perform maintenance in                              | true       |
| mflow1                             | Number   | [m³/h] Flow rate exch. 1                                | true       |
| mflow2                             | Number   | [m³/h] Flow rate exch. 2                                | true       |
| mflowreg1                          | Number   | [l/h] Regeneration flow rate Exchanger 1                | true       |
| mflowreg2                          | Number   | [l/h] Regeneration flow rate Exchanger 2                | true       |
| mflowblend                         | Number   | [m³/h] Blending flow rate                               | true       |
| mstep1                             | Number   | Step indication regeneration valve 1                    | true       |
| mstep2                             | Number   | Step indication regeneration valve 2                    | true       |
| mcurrent                           | Number   | [mA] Chlorine current                                   | true       |
| mreswatadmod                       | Number   | [m³] Absorber remaining amount of water                 | true       |
| msaltrange                         | Number   | [d] Salt-reach                                          | true       |
| mcountreg                          | Number   | Regeneration counter                                    | true       |
| mcountwater1                       | Number   | [l] Soft water exchanger 1                              | true       |
| mcountwater2                       | Number   | [l] Soft water exchanger 2                              | true       |
| mcountwatertank                    | Number   | [l] Make-up water volume                                | true       |
| msaltusage                         | Number   | [kg] salt consumption                                   | true       |
| mflowexc                           | Number   | [Min] during                                            | true       |
| mflowexc2reg1                      | Number   | [Min]                                                   | true       |
| mflowexc1reg2                      | Number   | [Min]                                                   | true       |
| mlifeadsorb                        | Number   | [%] Absorber exhausted by                               | true       |
| mhardsoftw                         | Number   | [°dh] Actual value soft water hardness                  | true       |
| mcapacity                          | Number   | [m³x°dH] Capacity figure                                | true       |
| maverage                           | Number   | maverage                                                | true       |
| mstddev                            | Number   | mstddev                                                 | true       |
| mmax                               | Number   | mmax                                                    | true       |
| mpress                             | Number   | mpress                                                  | true       |
| mtemp                              | Number   | mtemp                                                   | true       |
| mflowmax                           | Number   | [m³/h] Flow rate peak value                             | true       |
| mflowmax1reg2                      | Number   | [m³/h] Exchanger 1 peak value                           | true       |
| mflowmax2reg1                      | Number   | [m³/h] Exchanger 2 peak value                           | true       |
| mendreg1                           | Text     | [hh:mm] Last regeneration Exchanger 1                   | true       |
| mendreg2                           | Text     | [hh:mm] Last regeneration Exchanger 2                   | true       |
| requestRegeneration                | Switch   | Request Regeneration                                    | false      |
| regenerationStatus                 | Number   | Regeneration progress                                   | true       |
| regenerationProgressIdRaw          | Number   | Regeneration progress id (raw)                          | true       |
| regenerationProgressDescription    | Text     | Regeneration progress description                       | true       |
| regenerationRemainingInCurrentStep | Text     | Regeneration remaining in the current regeneration step | true       |
| saltLevel                          | Number   | Salt level                                              | false      |
| lastAccountedSaltUsageDate         | DateTime | Last accounted Salt usage date                          | true       |
| resetSaltLevel                     | Switch   | Reset salt level                                        | false      |            
| regenerationActive                 | Switch   | Regeneration active                                     | true       |

## Full Example

### Things

```
Bridge gruenbeckcloud:bridge:12345 "Grünbeck Cloud" [ email="my@email.com", password="12345", refreshPeriod=60 ] {
	Thing softener BSXXXXXXXX "softliQ:SD21" [ name="name of my softener", id="softliQ.D/BSXXXXXXXX", serialNumber="BSXXXXXXXX", series="softliQ.D", saltCapacity=25000 ]
}
```

### Items

```
Switch softenerAllowEmail 												{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pallowemail" }
Switch softenerAllowPush												{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pallowpushnotification" }
Switch softenerAutoDLST													{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pdlstauto" }
Switch softenerNtpSync													{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pntpsync" }
Switch softenerFaultSignalContact										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pcfcontact" }
Switch softenerKnx														{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pknx" }
Switch softenerMonitorNominalFlow										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmonflow" }
Switch softenerMonitorDesinfaction										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmondisinf" }
Switch softenerLedFlashPreAlarmSaltSuply								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pledatsaltpre" }
Switch softenerAudioSignal												{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pbuzzer" }
Number softenerResidualCapacityLimit									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:prescaplimit" }
Number softenerCurrentSetpoint											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pcurrent" }
Number softenerCharge 													{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pload" }
Number softenerIntervalOfForcedRegeneration								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pforcedregdist" }
Number softenerDaysTillNextMaintenance									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmaintint" }
Number softenerRegenerationValveEndFrequency							{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pfreqregvalve" }
Number softenerBlendingValveEndFrequency								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pfreqblendvalve" }
Dimmer softenerLedBrightness											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pledbright" }
Number softenerAbsorberTreatmentVolume									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pvolume" }
Number softenerHardnessWaterRaw											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:prawhard" }
Number softenerHardnessWaterSoft										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:psetsoft" }
Number softenerPulseRateSoftWaterMeter									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:ppratesoftwater" }
Number softenerPulseRateBlendingWaterMeter								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pprateblending" }
Number softenerPulseRateRegenerationWaterMeter							{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pprateregwater" }
Number softenerCapacityFigureMonday			  							{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:psetcapmo" }
Number softenerCapacityFigureTuesday									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:psetcaptu" }
Number softenerCapacityFigureWednesday									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:psetcapwe" }
Number softenerCapacityFigureThursday									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:psetcapth" }
Number softenerCapacityFigureFriday										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:psetcapfr" }
Number softenerCapacityFigureSaturday									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:psetcapsa" }
Number softenerCapacityFigureSunday										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:psetcapsu" }
Number softenerNominalFlowRate											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pnomflow" }
Number softenerPpressurereg												{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:ppressurereg" }
Number softenerRegenerationMonitoringTime								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmonregmeter" }
Number softenerSaltingMonitoringTime									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmonsalting" }
Number softenerSlowRinse												{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:prinsing" }
Number softenerBackwash													{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pbackwash" }
Number softenerWashingOut												{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pwashingout" }
Number softenerVolumeSmallestCapMin										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pminvolmincap" }
Number softenerVolumeSmallestCapMax										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmaxvolmincap" }
Number softenerVolumeLargestCapMin										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pminvolmaxcap" }
Number softenerVolumeLargestCapMax										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmaxvolmaxcap" }
Number softenerLongestSwitchOnTimeCICell								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmaxdurdisinfect" }
Number softenerRemainingRegenerationTimeMax								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmaxresdurreg" }
String softenerAudioSignalReleaseFrom									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pbuzzfrom" }
String softenerAudioSignalReleaseTo										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pbuzzto" }
String softenerServiceComapnyEmail										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmailadress" }
String softenerServiceCompanyName										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pname" }
String softenerServiceCompanyPhone										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:ptelnr" }
Number softenerOperationMode											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmode" }
Number softenerCustomOperationModeMonday								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmodemo" }
Number softenerCustomOperationModeTuesday								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmodetu" }
Number softenerCustomOperationModeWednesday								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmodewe" }
Number softenerCustomOperationModeThursday								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmodeth" }
Number softenerCustomOperationModeFriday								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmodefr" }
Number softenerCustomOperationModeSaturday								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmodesa" }
Number softenerCustomOperationModeSunday								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmodesu" }
Number softenerLanguage													{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:planguage" }
Number softenerHardnessUnit												{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:phunit" }
Number softenerTimeOfRegeneration										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregmode" }
Number softenerProgrammableOutputFunction								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pprogout" }
Number softenerProgrammableInputFunction								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pprogin" }
Number softenerPowerFailureReaction										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:ppowerfail" }
Number softenerChlorineCellState										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmodedesinf" }
Number softenerLedState													{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pled" }
String softenerRegenerationTimeMonday1									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregmo1" }
String softenerRegenerationTimeMonday2									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregmo2" }
String softenerRegenerationTimeMonday3									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregmo3" }
String softenerRegenerationTimeTuesday1									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregtu1" }
String softenerRegenerationTimeTuesday2									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregtu2" }
String softenerRegenerationTimeTuesday3									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregtu3" }
String softenerRegenerationTimeWednesday1								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregwe1" }
String softenerRegenerationTimeWednesday2								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregwe2" }
String softenerRegenerationTimeWednesday3								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregwe3" }
String softenerRegenerationTimeThursday1								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregth1" }
String softenerRegenerationTimeThursday2								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregth2" }
String softenerRegenerationTimeThursday3								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregth3" }
String softenerRegenerationTimeFriday1									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregfr1" }
String softenerRegenerationTimeFriday2									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregfr2" }
String softenerRegenerationTimeFriday3									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregfr3" }
String softenerRegenerationTimeSaturday1								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregsa1" }
String softenerRegenerationTimeSaturday2								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregsa2" }
String softenerRegenerationTimeSaturday3								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregsa3" }
String softenerRegenerationTimeSunday1									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregsu1" }
String softenerRegenerationTimeSunday2									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregsu2" }
String softenerRegenerationTimeSunday3									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pregsu3" }
Number softenerMonitoringBlending										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pmonblend" }
Number softenerSystemOverladed											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:poverload" }
Number softenerRegenerationValve2EndFrequency							{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:pfreqregvalve2" }
String softenerHardwareVersion											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:hardwareVersion" }
DateTime softenerLastService	 										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:lastService" }
Number softenerCurrentOperationMode										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mode" }
DateTime softenerNextRegeneration										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:nextRegeneration" }
Number softenerNominalFlow												{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:nominalFlow" }
Number softenerCurrentHardnessWaterRaw									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:rawWater" }
Number softenerCurrentHardnessWaterSoft									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:softWater" }
String softenerSoftwareVersion											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:softwareVersion" }
String softenerTimeZone													{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:timeZone" }
Number softenerCurrentHardnessUnit										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:unit" }
DateTime softenerStartup												{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:startup" }
Number softenerModelType												{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:type" }
Switch softenerError													{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:hasError" }
Switch softenerRegistred												{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:register" }
String softenerLastErrorMessage											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:last_error_message" }
Switch softenerLastErrorResolved										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:last_error_resolved" }
DateTime softenerLastErrorDate											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:last_error_date" }
String softenerLastErrorType											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:last_error_type" }
DateTime softenerUsageSaltDate1											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:usage_date_salt_1" }
Number softenerUsageSaltValue1											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:usage_value_salt_1" }
DateTime softenerUsageSaltDate2											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:usage_date_salt_2" }
Number softenerUsageSaltValue2											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:usage_value_salt_2" }
DateTime softenerUsageSaltDate3											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:usage_date_salt_3" }
Number softenerUsageSaltValue3											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:usage_value_salt_3" }
DateTime softenerUsageWaterDate1										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:usage_date_water_1" }
Number softenerUsageWaterValue1											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:usage_value_water_1" }
DateTime softenerUsageWaterDate2										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:usage_date_water_2" }
Number softenerUsageWaterValue2											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:usage_value_water_2" }
DateTime softenerUsageWaterDate3										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:usage_date_water_3" }
Number softenerUsageWaterValue3											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:usage_value_water_3" }
Switch softenerIbuiltindev												{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:ibuiltindev" }
Number softenerIsncu													{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:isncu" }
Number softenerPercentTillRegeneration1									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mregpercent1" }
Number softenerPercentTillRegeneration2									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mregpercent2" }
Number softenerRegenerationStepRemainingTime							{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mremregstep" }
Number softenerRegenerationStep											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mregstatus" }
Number softenerResidualCapacity1										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mresidcap1" }
Number softenerResidualCapacity2										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mresidcap2" }
Number softenerSoftWaterExchangerCapacity1								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mrescapa1" }
Number softenerSoftWaterExchangerCapacity2								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mrescapa2" }
Number softenerMaintenanceInDays										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mmaint" }
Number softenerFlowRateExchanger1										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mflow1" }
Number softenerFlowRateExchanger2										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mflow2" }
Number softenerRegenerationFlowRateExchanger1							{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mflowreg1" }
Number softenerRegenerationFlowRateExchanger2							{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mflowreg2" }
Number softenerBlendingFlowRate											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mflowblend" }
Number softenerStepIndicationRegenerationValve1							{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mstep1" }
Number softenerStepIndicationRegenerationValve2							{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mstep2" }
Number softenerChlorineCurrent											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mcurrent" }
Number softenerAbsorberRemainingWater									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mreswatadmod" }
Number softenerSaltReach												{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:msaltrange" }
Number softenerRegenerationCounter										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mcountreg" }
Number softenerSoftWaterExchanger1										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mcountwater1" }
Number softenerSoftWaterExchanger2										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mcountwater2" }
Number softenerMakeUpWaterVolume										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mcountwatertank" }
Number softenerSaltConsumption											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:msaltusage" }
Number softenerMflowexc													{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mflowexc" }
Number softenerMflowexc2reg1											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mflowexc2reg1" }
Number softenerMflowexc1reg2											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mflowexc1reg2" }
Number softenerAbsorberExhaustedBy										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mlifeadsorb" }
Number softenerActualHardnessSoftWater									{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mhardsoftw" }
Number softenerCapacityFigure											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mcapacity" }
Number softenerMaverage													{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:maverage" }
Number softenerMstddev													{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mstddev" }
Number softenerMmax														{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mmax" }
Number softenerMpress													{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mpress" }
Number softenerMtemp													{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mtemp" }
Number softenerPeakValueFlowRate										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mflowmax" }
Number softenerPeakValueExchanger1										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mflowmax1reg2" }
Number softenerPeakValueExchanger2										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mflowmax2reg1" }
String softenerLastRegenerationTimeExchanger1							{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mendreg1" }
String softenerLastRegenerationTimeExchanger2							{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:mendreg2" }


// important
Switch softenerRequestRegeneration										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:requestRegeneration" }
Number softenerRegenerationStatus										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:regenerationStatus" }
Number softenerRegenerationProgressIdRaw								{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:regenerationProgressIdRaw" }
String softenerRegenerationProgressDescription							{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:regenerationProgressDescription" }
String softenerRegenerationRemainingInCurrentStep						{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:regenerationRemainingInCurrentStep" }
Number softenerCurrentSaltLevel											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:saltLevel" }
DateTime softenerLastAccountedSaltUsageDate             				{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:lastAccountedSaltUsageDate" }
Switch softenerResetSaltLevel											{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:resetSaltLevel" }
Switch softenerRegenerationActive										{ channel="gruenbeckcloud:softener:12345:BSXXXXXXXX:regenerationActive" }
```

