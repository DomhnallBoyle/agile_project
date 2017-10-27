using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC3045_CS2.Utility
{
    /// <summary>
    /// Class that serializes PascalCase into camelCase
    /// </summary>
    class CamelCaseSerializationStrategy : PocoJsonSerializerStrategy
    {
        protected override string MapClrMemberNameToJsonFieldName(string clrPropertyName)
        {
            return Char.ToLower(clrPropertyName[0]) + clrPropertyName.Substring(1);
        }
    }
}
