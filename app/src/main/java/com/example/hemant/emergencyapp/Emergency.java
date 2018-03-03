package com.example.hemant.emergencyapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Emergency extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST = 1, REQUEST_CODE = 1;
    private ImageButton pol, wom, amb, fir, emerg;
    private ImageButton donation, suggestion, compl, contact, misccontact, emrgcontact, atdetails;
    private CheckBox emrgpol, emrgwom, emrgFrnd;
    private TextView atname;
    private MediaPlayer mp;
    private  AudioManager am;
    DrawerLayout mLayout;
    ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    LinearLayout emrgLay, fragSugg;
    private AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if (i == AudioManager.AUDIOFOCUS_LOSS)
                releaseMediaPlayer();
            else if (i == AudioManager.AUDIOFOCUS_GAIN)
                mp.start();
            else if (i == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT || i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mp.pause();
                mp.seekTo(0);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency);
        mLayout = (DrawerLayout) findViewById(R.id.drawer);
        emrgLay = (LinearLayout) findViewById(R.id.emergLay);
        fragSugg = (LinearLayout) findViewById(R.id.fragSugg);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        mToggle = new ActionBarDrawerToggle(Emergency.this, mLayout, R.string.opne, R.string.close);
        mLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        pol = (ImageButton) findViewById(R.id.pol);
        wom = (ImageButton) findViewById(R.id.wom);
        amb = (ImageButton) findViewById(R.id.amb);
        fir = (ImageButton) findViewById(R.id.fir);
        emerg = (ImageButton) findViewById(R.id.emerg);
        suggestion = (ImageButton) findViewById(R.id.suggestion);
        contact = (ImageButton) findViewById(R.id.contact);
        compl = (ImageButton) findViewById(R.id.compl);
        donation = (ImageButton) findViewById(R.id.donation);
        atdetails = (ImageButton) findViewById(R.id.atdetails);
        atname = (TextView) findViewById(R.id.atname);
        emrgcontact = (ImageButton) findViewById(R.id.emrgcontact);
        misccontact = (ImageButton) findViewById(R.id.miscontact);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sp = getSharedPreferences("my_sp", MODE_PRIVATE);
        if (sp != null) {
            String name = sp.getString("k1", null);
            atname.setText(name);
        }
//        else{
//            String name = sp2.getString("k1",null);
//            atname.setText(name);
//        }
        // Police Button method Implementattion
        pol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu pm = new PopupMenu(Emergency.this, pol);
                pm.getMenuInflater().inflate(R.menu.my_action, pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Call Button
                        if (item.getItemId() == R.id.call) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel: 9416824432"));
                            if (ActivityCompat.checkSelfPermission(Emergency.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSION_REQUEST);
                            } else
                                startActivity(intent);
                        }
                        // Alert Button
                        else if (item.getItemId() == R.id.alrt) {
                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("smsto: 9416824432"));
                            intent.putExtra("sms_body", "Please Help Me");
                            if (ActivityCompat.checkSelfPermission(Emergency.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST);
                            } else
                                startActivity(intent);
                        }
                        // Proof Capture
                        else {
                            AlertDialog.Builder adb = new AlertDialog.Builder(Emergency.this);
                            View vv = getLayoutInflater().inflate(R.layout.dialog_capture, null);
                            adb.setView(vv);
                            adb.setCancelable(true);
                            adb.show();
                            ImageButton video = vv.findViewById(R.id.video);
                            ImageButton image = vv.findViewById(R.id.img);
                            // Image Capture
                            image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (intent.resolveActivity(getPackageManager()) != null)
                                        startActivityForResult(intent, REQUEST_CODE);
                                }
                            });
                            // Video Capture
                            video.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                    if (intent.resolveActivity(getPackageManager()) != null)
                                        startActivityForResult(intent, REQUEST_CODE);
                                }
                            });

                        }
                        return false;
                    }
                });
                pm.show();
            }
        });
        // Women PCR Button method Implementattion

        wom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu pm = new PopupMenu(Emergency.this, pol);
                pm.getMenuInflater().inflate(R.menu.my_action, pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Call Button
                        if (item.getItemId() == R.id.call) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel: 9416824432"));
                            if (ActivityCompat.checkSelfPermission(Emergency.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSION_REQUEST);
                            } else
                                startActivity(intent);
                        }
                        // Alert Button
                        else if (item.getItemId() == R.id.alrt) {
                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("smsto: 9416824432"));
                            intent.putExtra("sms_body", "Please Help Me");
                            if (ActivityCompat.checkSelfPermission(Emergency.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST);
                            } else
                                startActivity(intent);
                        }
                        // Proof Button
                        else {
                            AlertDialog.Builder adb = new AlertDialog.Builder(Emergency.this);
                            View vv = getLayoutInflater().inflate(R.layout.dialog_capture, null);
                            adb.setView(vv);
                            adb.setCancelable(true);
                            adb.show();
                            ImageButton video = vv.findViewById(R.id.video);
                            ImageButton image = vv.findViewById(R.id.img);
                            // image Capture
                            image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (intent.resolveActivity(getPackageManager()) != null)
                                        startActivityForResult(intent, REQUEST_CODE);
                                }
                            });
                            // Video Capture
                            video.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                    if (intent.resolveActivity(getPackageManager()) != null)
                                        startActivityForResult(intent, REQUEST_CODE);
                                }
                            });

                        }
                        return false;
                    }
                });
                pm.show();
            }
        });
        // Fire Button method Implementattion
        fir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu pm = new PopupMenu(Emergency.this, pol);
                pm.getMenuInflater().inflate(R.menu.my_action, pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //Call Button
                        if (item.getItemId() == R.id.call) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel: 9416824432"));
                            if (ActivityCompat.checkSelfPermission(Emergency.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSION_REQUEST);
                            } else
                                startActivity(intent);
                        }
                        // Alert Button
                        else if (item.getItemId() == R.id.alrt) {
                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("smsto: 9416824432"));
                            intent.putExtra("sms_body", "Please Help Me");
                            if (ActivityCompat.checkSelfPermission(Emergency.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST);
                            } else
                                startActivity(intent);
                        }
                        //Proof Button
                        else {
                            AlertDialog.Builder adb = new AlertDialog.Builder(Emergency.this);
                            View vv = getLayoutInflater().inflate(R.layout.dialog_capture, null);
                            adb.setView(vv);
                            adb.setCancelable(true);
                            adb.show();
                            ImageButton video = vv.findViewById(R.id.video);
                            ImageButton image = vv.findViewById(R.id.img);
                            //Image Capture
                            image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (intent.resolveActivity(getPackageManager()) != null)
                                        startActivityForResult(intent, REQUEST_CODE);
                                }
                            });
                            //Video Capture
                            video.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                    if (intent.resolveActivity(getPackageManager()) != null)
                                        startActivityForResult(intent, REQUEST_CODE);
                                }
                            });

                        }
                        return false;
                    }
                });
                pm.show();
            }
        });
        // Ambulance Button method Implementattion
        amb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu pm = new PopupMenu(Emergency.this, pol);
                pm.getMenuInflater().inflate(R.menu.my_action, pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.call) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel: 9416824432"));
                            if (ActivityCompat.checkSelfPermission(Emergency.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSION_REQUEST);
                            } else
                                startActivity(intent);
                        } else if (item.getItemId() == R.id.alrt) {
                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("smsto: 9416824432"));
                            intent.putExtra("sms_body", "SMS sending test");
                            if (ActivityCompat.checkSelfPermission(Emergency.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST);
                            } else
                                startActivity(intent);
                        } else {
                            AlertDialog.Builder adb = new AlertDialog.Builder(Emergency.this);
                            View vv = getLayoutInflater().inflate(R.layout.dialog_capture, null);
                            adb.setView(vv);
                            adb.setCancelable(true);
                            adb.show();
                            ImageButton video = vv.findViewById(R.id.video);
                            ImageButton image = vv.findViewById(R.id.img);
                            image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (intent.resolveActivity(getPackageManager()) != null)
                                        startActivityForResult(intent, REQUEST_CODE);
                                }
                            });
                            video.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                    if (intent.resolveActivity(getPackageManager()) != null)
                                        startActivityForResult(intent, REQUEST_CODE);
                                }
                            });

                        }
                        return false;
                    }
                });
                pm.show();
            }
        });
        //======================Emergency Button=======================
        emerg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adb = new AlertDialog.Builder(Emergency.this);
                View vv = getLayoutInflater().inflate(R.layout.my_emergency, null);
                adb.setView(vv);
                adb.setCancelable(true);
                AlertDialog ad = adb.show();
                emrgpol = vv.findViewById(R.id.emergpol);
                emrgwom = vv.findViewById(R.id.emergwom);
                emrgFrnd = vv.findViewById(R.id.emergfrnd);

            }
        });
        // Contact button ===================================================
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adb = new AlertDialog.Builder(Emergency.this);
                View vv = getLayoutInflater().inflate(R.layout.my_contact, null);
                adb.setView(vv);
                adb.setCancelable(true);
                adb.show();

            }
        });
        // Complaint Button =====================================
        compl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emrgLay.setVisibility(View.INVISIBLE);
                fragSugg.setVisibility(View.VISIBLE);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragSugg, new My_complaint());
                ft.commit();
            }
        });
//        // Suggestion button ======================================================
        suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emrgLay.setVisibility(View.INVISIBLE);
                fragSugg.setVisibility(View.VISIBLE);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragSugg, new My_suggestion());
                ft.commit();
            }
        });
        donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.giveindia.org")));
            }
        });
        emrgcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adb = new AlertDialog.Builder(Emergency.this);
                View vv = getLayoutInflater().inflate(R.layout.emergency_contact, null);
                adb.setView(vv);
                adb.setCancelable(true);
                final AlertDialog ad = adb.show();
                final EditText name = vv.findViewById(R.id.name);
                EditText number = vv.findViewById(R.id.number);
                Button bt = vv.findViewById(R.id.subnumber);
                final SharedPreferences sp = getSharedPreferences("my_sp", MODE_PRIVATE);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences.Editor et = sp.edit();
                        et.putString("k5", name.getText().toString());
                        et.commit();
                        ad.dismiss();
                    }
                });
            }
        });
        atdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Emergency.this, EmergencyDetails.class));
            }
        });
        misccontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emrgLay.setVisibility(View.INVISIBLE);
                fragSugg.setVisibility(View.VISIBLE);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragSugg, new EmegencyService());
                ft.commit();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mLayout.closeDrawer(GravityCompat.START);
                if (item.getItemId() == R.id.myhome) {
                    if (fragSugg.getVisibility() == View.VISIBLE) {
                        fragSugg.setVisibility(View.INVISIBLE);
                        emrgLay.setVisibility(View.VISIBLE);
                    } else
                        Toast.makeText(Emergency.this, "HOME", Toast.LENGTH_SHORT);
                } else if (item.getItemId() == R.id.emrgcontact) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(Emergency.this);
                    View vv = getLayoutInflater().inflate(R.layout.emergency_contact, null);
                    adb.setView(vv);
                    adb.setCancelable(true);
                    final AlertDialog ad = adb.show();
                    final EditText name = vv.findViewById(R.id.name);
                    final EditText number = vv.findViewById(R.id.number);
                    Button bt = vv.findViewById(R.id.subnumber);
                    final SharedPreferences sp = getSharedPreferences("my_sp", MODE_PRIVATE);
                    bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharedPreferences.Editor et = sp.edit();
                            et.putString("k5", name.getText().toString());
                            et.putString("k6", number.getText().toString());
                            et.commit();
                            ad.dismiss();
                        }
                    });


                } else if (item.getItemId() == R.id.complaint) {
                    emrgLay.setVisibility(View.INVISIBLE);
                    fragSugg.setVisibility(View.VISIBLE);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragSugg, new My_complaint());
                    ft.commit();
                } else if (item.getItemId() == R.id.emrgservice) {
                    emrgLay.setVisibility(View.INVISIBLE);
                    fragSugg.setVisibility(View.VISIBLE);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragSugg, new EmegencyService());
                    ft.commit();

                } else if (item.getItemId() == R.id.donation) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.giveindia.org")));
                } else if (item.getItemId() == R.id.suggst) {
                    emrgLay.setVisibility(View.INVISIBLE);
                    fragSugg.setVisibility(View.VISIBLE);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragSugg, new My_suggestion());
                    ft.commit();
                } else if (item.getItemId() == R.id.contact) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(Emergency.this);
                    View vv = getLayoutInflater().inflate(R.layout.my_contact, null);
                    adb.setView(vv);
                    adb.setCancelable(true);
                    adb.show();
                } else if (item.getItemId() == R.id.lgout) {
                    EmergencyDetails ed = new EmergencyDetails();
                    SharedPreferences sp = getSharedPreferences("my_sp", MODE_PRIVATE);
                    SharedPreferences.Editor et = sp.edit();
                    et.clear();
                    et.commit();
                    startActivity(new Intent(Emergency.this, HomeActivity.class));
                    pause();
                    ed.pause();
                }
                return false;
            }
        });
    }

    public void AlertSubmit(View view) {
        if (emrgpol.isChecked()) {
            String msg = "Please Help ME";
            String telNr = "9138459808";
            if (ContextCompat.checkSelfPermission(Emergency.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST);
            } else {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(telNr, null, msg, null, null);
            }
        }
        if (emrgwom.isChecked()) {
            String msg = "Please Help ME";
            String telNr = "9416824432";
            if (ContextCompat.checkSelfPermission(Emergency.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST);
            } else {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(telNr, null, msg, null, null);
            }
        }
        if (emrgFrnd.isChecked()) {
            SharedPreferences sp = getSharedPreferences("my_sp", MODE_PRIVATE);
            SharedPreferences sp2 = getSharedPreferences("my_log", MODE_PRIVATE);
            String telNr;
//            if(sp!=null) {
            telNr = sp.getString("k6", null);
//            }
//            else{
//                 telNr = sp2.getString("k6",null);
//            }

            String msg = "Please Help ME";
            if (ContextCompat.checkSelfPermission(Emergency.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST);
            } else {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(telNr, null, msg, null, null);
            }
        }
        Toast.makeText(Emergency.this, "SEND", Toast.LENGTH_SHORT).show();
    }

    public void PolSiren(View view) {
        AlertDialog.Builder adb = new AlertDialog.Builder(Emergency.this);
        View vv = getLayoutInflater().inflate(R.layout.play_dialog, null);
        adb.setView(vv);
        adb.setCancelable(true);
        adb.show();
        ImageButton play = vv.findViewById(R.id.play);
        ImageButton stop = vv.findViewById(R.id.stop);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseMediaPlayer();
                int result = am.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.policesiren);
                    mp.start();
                    mp.setLooping(true);
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               releaseMediaPlayer();
            }
        });
    }

    public void Whistle(View view) {
        AlertDialog.Builder adb = new AlertDialog.Builder(Emergency.this);
        View vv = getLayoutInflater().inflate(R.layout.play_dialog, null);
        adb.setView(vv);
        adb.setCancelable(true);
        adb.show();
        ImageButton play = vv.findViewById(R.id.play);
        ImageButton stop = vv.findViewById(R.id.stop);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseMediaPlayer();
                int result = am.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.whistle);
                    mp.start();

                }
                mp.setLooping(true);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseMediaPlayer();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (fragSugg.getVisibility() == View.VISIBLE) {
            fragSugg.setVisibility(View.INVISIBLE);
            emrgLay.setVisibility(View.VISIBLE);
        } else
            super.onBackPressed();
    }

    public void pause() {
        finish();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mp != null) {
            mp.release();
            mp = null;
            am.abandonAudioFocus(afChangeListener);
        }
    }
}