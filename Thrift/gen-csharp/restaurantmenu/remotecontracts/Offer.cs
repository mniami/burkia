/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.IO;
using Thrift;
using Thrift.Collections;
using System.Runtime.Serialization;
using Thrift.Protocol;
using Thrift.Transport;

namespace restaurantmenu.remotecontracts
{

  /// <summary>
  /// Structs are the basic complex data structures. They are comprised of fields
  /// which each have an integer identifier, a type, a symbolic name, and an
  /// optional default value.
  /// 
  /// Fields can be declared "optional", which ensures they will not be included
  /// in the serialized output if they aren't set.  Note that this requires some
  /// manual management in some languages.
  /// </summary>
  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class Offer : TBase
  {
    private string _name;
    private string _description;
    private string _imageUrl;
    private List<Offer> _offers;
    private int _type;

    public string Name
    {
      get
      {
        return _name;
      }
      set
      {
        __isset.name = true;
        this._name = value;
      }
    }

    public string Description
    {
      get
      {
        return _description;
      }
      set
      {
        __isset.description = true;
        this._description = value;
      }
    }

    public string ImageUrl
    {
      get
      {
        return _imageUrl;
      }
      set
      {
        __isset.imageUrl = true;
        this._imageUrl = value;
      }
    }

    public List<Offer> Offers
    {
      get
      {
        return _offers;
      }
      set
      {
        __isset.offers = true;
        this._offers = value;
      }
    }

    public int Type
    {
      get
      {
        return _type;
      }
      set
      {
        __isset.type = true;
        this._type = value;
      }
    }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool name;
      public bool description;
      public bool imageUrl;
      public bool offers;
      public bool type;
    }

    public Offer() {
    }

    public void Read (TProtocol iprot)
    {
      TField field;
      iprot.ReadStructBegin();
      while (true)
      {
        field = iprot.ReadFieldBegin();
        if (field.Type == TType.Stop) { 
          break;
        }
        switch (field.ID)
        {
          case 1:
            if (field.Type == TType.String) {
              Name = iprot.ReadString();
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 2:
            if (field.Type == TType.String) {
              Description = iprot.ReadString();
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 3:
            if (field.Type == TType.String) {
              ImageUrl = iprot.ReadString();
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 4:
            if (field.Type == TType.List) {
              {
                Offers = new List<Offer>();
                TList _list0 = iprot.ReadListBegin();
                for( int _i1 = 0; _i1 < _list0.Count; ++_i1)
                {
                  Offer _elem2;
                  _elem2 = new Offer();
                  _elem2.Read(iprot);
                  Offers.Add(_elem2);
                }
                iprot.ReadListEnd();
              }
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 5:
            if (field.Type == TType.I32) {
              Type = iprot.ReadI32();
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          default: 
            TProtocolUtil.Skip(iprot, field.Type);
            break;
        }
        iprot.ReadFieldEnd();
      }
      iprot.ReadStructEnd();
    }

    public void Write(TProtocol oprot) {
      TStruct struc = new TStruct("Offer");
      oprot.WriteStructBegin(struc);
      TField field = new TField();
      if (Name != null && __isset.name) {
        field.Name = "name";
        field.Type = TType.String;
        field.ID = 1;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(Name);
        oprot.WriteFieldEnd();
      }
      if (Description != null && __isset.description) {
        field.Name = "description";
        field.Type = TType.String;
        field.ID = 2;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(Description);
        oprot.WriteFieldEnd();
      }
      if (ImageUrl != null && __isset.imageUrl) {
        field.Name = "imageUrl";
        field.Type = TType.String;
        field.ID = 3;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(ImageUrl);
        oprot.WriteFieldEnd();
      }
      if (Offers != null && __isset.offers) {
        field.Name = "offers";
        field.Type = TType.List;
        field.ID = 4;
        oprot.WriteFieldBegin(field);
        {
          oprot.WriteListBegin(new TList(TType.Struct, Offers.Count));
          foreach (Offer _iter3 in Offers)
          {
            _iter3.Write(oprot);
          }
          oprot.WriteListEnd();
        }
        oprot.WriteFieldEnd();
      }
      if (__isset.type) {
        field.Name = "type";
        field.Type = TType.I32;
        field.ID = 5;
        oprot.WriteFieldBegin(field);
        oprot.WriteI32(Type);
        oprot.WriteFieldEnd();
      }
      oprot.WriteFieldStop();
      oprot.WriteStructEnd();
    }

    public override string ToString() {
      StringBuilder __sb = new StringBuilder("Offer(");
      bool __first = true;
      if (Name != null && __isset.name) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Name: ");
        __sb.Append(Name);
      }
      if (Description != null && __isset.description) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Description: ");
        __sb.Append(Description);
      }
      if (ImageUrl != null && __isset.imageUrl) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("ImageUrl: ");
        __sb.Append(ImageUrl);
      }
      if (Offers != null && __isset.offers) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Offers: ");
        __sb.Append(Offers);
      }
      if (__isset.type) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Type: ");
        __sb.Append(Type);
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }

}
