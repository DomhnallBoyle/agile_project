using RestSharp.Deserializers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC3045_CS2.Models
{
    public class TaskEstimation
    {
        [DeserializeAs(Name = "taskId")]
        public long TaskId { get; set; }

        [DeserializeAs(Name = "date")]
        public DateTime Date { get; set; }

        [DeserializeAs(Name = "estimate")]
        public int Estimate { get; set; }

        public TaskEstimation() { }
    }
}
